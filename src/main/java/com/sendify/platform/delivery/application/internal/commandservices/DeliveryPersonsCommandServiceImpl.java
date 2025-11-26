package com.sendify.platform.delivery.application.internal.commandservices;

import com.sendify.platform.delivery.domain.exceptions.DeliveryPersonNotFoundException;
import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import com.sendify.platform.delivery.domain.model.commands.CreateDeliveryPersonCommand;
import com.sendify.platform.delivery.domain.model.commands.UpdateDeliveryPersonCommand;
import com.sendify.platform.delivery.domain.model.valueobjects.DeliveryPersonCode;
import com.sendify.platform.delivery.domain.services.DeliveryPersonsCommandService;
import com.sendify.platform.delivery.infrastructure.persistence.jpa.repositories.DeliveryPersonRepository;
import com.sendify.platform.shared.domain.model.valueobjects.PhoneNumber;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DeliveryPersonsCommandServiceImpl implements DeliveryPersonsCommandService {

    private final DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryPersonsCommandServiceImpl(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    @Override
    public DeliveryPerson handle(CreateDeliveryPersonCommand command) {

        // Generar código si no viene
        String code = command.code();
        if (code == null || code.isBlank()) {
            code = generateUniqueCode();
        } else {
            code = command.code().trim().toUpperCase();
            if (deliveryPersonRepository.existsByCodeValue(code)) {
                throw new IllegalArgumentException("Delivery person code must be unique");
            }
        }

        var deliveryPerson = DeliveryPerson.create(
                command.name(),
                new DeliveryPersonCode(code),
                new PhoneNumber(command.phone())
        );

        return deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public DeliveryPerson handle(UpdateDeliveryPersonCommand command) {

        var deliveryPerson = deliveryPersonRepository.findById(command.deliveryPersonId())
                .orElseThrow(() -> new DeliveryPersonNotFoundException(command.deliveryPersonId()));

        PhoneNumber phone = command.phone() != null ? new PhoneNumber(command.phone()) : null;

        deliveryPerson.update(
                command.name(),
                phone,
                command.isActive()
        );

        return deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public void delete(Long deliveryPersonId) {
        if (!deliveryPersonRepository.existsById(deliveryPersonId)) {
            throw new DeliveryPersonNotFoundException(deliveryPersonId);
        }
        deliveryPersonRepository.deleteById(deliveryPersonId);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = DeliveryPersonCode.generateRandom();
        } while (deliveryPersonRepository.existsByCodeValue(code));
        return code;
    }
}
