package com.sendify.platform.couriers.domain.model.aggregates;

import com.sendify.platform.couriers.domain.model.valueobjects.MoneyAmount;
import com.sendify.platform.couriers.domain.model.valueobjects.WebsiteUrl;
import com.sendify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.sendify.platform.shared.domain.model.valueobjects.EmailAddress;
import com.sendify.platform.shared.domain.model.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "couriers")
public class Courier extends AuditableAbstractAggregateRoot<Courier> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "contact_email", nullable = false, length = 160))
    })
    private EmailAddress contactEmail;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "contact_phone", nullable = false, length = 40))
    })
    private PhoneNumber contactPhone;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "website_url", length = 200))
    })
    private WebsiteUrl websiteUrl;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "cost_per_kg", nullable = false, precision = 10, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false, length = 3))
    })
    private MoneyAmount costPerKg;

    @Column(name = "estimated_delivery_days", nullable = false)
    private Integer estimatedDeliveryDays;

    private Courier(String name,
                    EmailAddress contactEmail,
                    PhoneNumber contactPhone,
                    WebsiteUrl websiteUrl,
                    MoneyAmount costPerKg,
                    Integer estimatedDeliveryDays) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Courier name must not be blank");
        }
        if (estimatedDeliveryDays == null || estimatedDeliveryDays <= 0) {
            throw new IllegalArgumentException("Estimated delivery days must be greater than zero");
        }

        this.name = name.trim();
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.websiteUrl = websiteUrl;
        this.costPerKg = costPerKg;
        this.estimatedDeliveryDays = estimatedDeliveryDays;
    }

    // Factory method
    public static Courier create(String name,
                                 EmailAddress contactEmail,
                                 PhoneNumber contactPhone,
                                 WebsiteUrl websiteUrl,
                                 MoneyAmount costPerKg,
                                 Integer estimatedDeliveryDays) {
        return new Courier(name, contactEmail, contactPhone, websiteUrl, costPerKg, estimatedDeliveryDays);
    }

    public void update(String name,
                       EmailAddress contactEmail,
                       PhoneNumber contactPhone,
                       WebsiteUrl websiteUrl,
                       MoneyAmount costPerKg,
                       Integer estimatedDeliveryDays) {

        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }
        if (contactEmail != null) {
            this.contactEmail = contactEmail;
        }
        if (contactPhone != null) {
            this.contactPhone = contactPhone;
        }
        if (websiteUrl != null) {
            this.websiteUrl = websiteUrl;
        }
        if (costPerKg != null) {
            this.costPerKg = costPerKg;
        }
        if (estimatedDeliveryDays != null && estimatedDeliveryDays > 0) {
            this.estimatedDeliveryDays = estimatedDeliveryDays;
        }
    }
}
