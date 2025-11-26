package com.sendify.platform.shared.infrastructure.persistence.jpa.configuration.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Convierte nombres de entidades/campos a snake_case
 * y pluraliza el nombre de las tablas de forma simple.
 *
 * Ejemplo:
 *  - Shipment -> shipments
 *  - DeliveryPerson -> delivery_persons
 */
public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy
        extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String snake = toSnakeCase(name.getText());
        String plural = pluralize(snake);
        return Identifier.toIdentifier(plural);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String snake = toSnakeCase(name.getText());
        return Identifier.toIdentifier(snake);
    }

    private String toSnakeCase(String text) {
        if (text == null) return null;
        String snake = text
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("([A-Z])([A-Z][a-z])", "$1_$2")
                .toLowerCase();
        return snake;
    }

    private String pluralize(String text) {
        if (text == null || text.isBlank()) return text;
        // Pluralización ultra simple: si no termina en 's', agrega 's'
        if (text.endsWith("s")) return text;
        return text + "s";
    }
}
