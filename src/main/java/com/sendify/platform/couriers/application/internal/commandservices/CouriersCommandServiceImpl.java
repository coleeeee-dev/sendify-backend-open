package com.sendify.platform.couriers.application.internal.commandservices;

import com.sendify.platform.couriers.domain.exceptions.CourierNotFoundException;
import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import com.sendify.platform.couriers.domain.model.commands.CreateCourierCommand;
import com.sendify.platform.couriers.domain.model.commands.UpdateCourierCommand;
import com.sendify.platform.couriers.domain.model.valueobjects.MoneyAmount;
import com.sendify.platform.couriers.domain.model.valueobjects.WebsiteUrl;
import com.sendify.platform.couriers.domain.services.CouriersCommandService;
import com.sendify.platform.couriers.infrastructure.persistence.jpa.repositories.CourierRepository;
import com.sendify.platform.shared.domain.model.valueobjects.EmailAddress;
import com.sendify.platform.shared.domain.model.valueobjects.PhoneNumber;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class CouriersCommandServiceImpl implements CouriersCommandService {

    private final CourierRepository courierRepository;

    public CouriersCommandServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public Courier handle(CreateCourierCommand command) {
        EmailAddress email = new EmailAddress(command.contactEmail());
        PhoneNumber phone = new PhoneNumber(command.contactPhone());
        WebsiteUrl websiteUrl = new WebsiteUrl(command.websiteUrl());
        MoneyAmount costPerKg = new MoneyAmount(
                command.costPerKg(),
                command.currency() != null ? command.currency() : "USD"
        );

        Courier courier = Courier.create(
                command.name(),
                email,
                phone,
                websiteUrl,
                costPerKg,
                command.estimatedDeliveryDays()
        );

        return courierRepository.save(courier);
    }

    @Override
    public Courier handle(UpdateCourierCommand command) {
        Courier courier = courierRepository.findById(command.courierId())
                .orElseThrow(() -> new CourierNotFoundException(command.courierId()));

        EmailAddress email = command.contactEmail() != null ? new EmailAddress(command.contactEmail()) : null;
        PhoneNumber phone = command.contactPhone() != null ? new PhoneNumber(command.contactPhone()) : null;
        WebsiteUrl websiteUrl = new WebsiteUrl(command.websiteUrl());
        MoneyAmount costPerKg = null;

        BigDecimal amount = command.costPerKg();
        if (amount != null) {
            costPerKg = new MoneyAmount(
                    amount,
                    command.currency() != null ? command.currency() : "USD"
            );
        }

        courier.update(
                command.name(),
                email,
                phone,
                websiteUrl,
                costPerKg,
                command.estimatedDeliveryDays()
        );

        return courierRepository.save(courier);
    }
}
