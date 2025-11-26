package com.sendify.platform.shared.interfaces.rest.error;

import com.sendify.platform.shared.interfaces.rest.resources.MessageResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejo global de errores, con soporte de i18n vía Accept-Language.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private Locale resolveLocale(HttpServletRequest request) {
        // Spring ya usa Accept-Language para el Locale del request
        return request.getLocale();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Locale locale = resolveLocale(request);

        String globalMessage = messageSource.getMessage(
                "error.validation", null, "Validation failed", locale);

        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", globalMessage,
                        "errors", fieldErrors
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResource> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        // Aquí podrías mapear a 400 con el mensaje tal cual
        return ResponseEntity
                .badRequest()
                .body(MessageResource.of(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResource> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        Locale locale = resolveLocale(request);
        String msg = messageSource.getMessage(
                "error.generic", null, "Unexpected error", locale);

        // En producción no mostraríamos ex.getMessage() al usuario,
        // pero para el curso esto está bien.
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageResource.of(msg));
    }
}
