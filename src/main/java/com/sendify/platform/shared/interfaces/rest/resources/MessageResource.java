package com.sendify.platform.shared.interfaces.rest.resources;

/**
 * Recurso simple para devolver mensajes (i18n) en errores o respuestas genéricas.
 */
public class MessageResource {

    private final String message;

    public MessageResource(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static MessageResource of(String message) {
        return new MessageResource(message);
    }
}
