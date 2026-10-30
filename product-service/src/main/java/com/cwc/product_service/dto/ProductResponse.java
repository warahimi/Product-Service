package com.cwc.product_service.dto;


import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        boolean active,
        Long version,
        Instant createdAt,
        Instant updatedAt
) {}

