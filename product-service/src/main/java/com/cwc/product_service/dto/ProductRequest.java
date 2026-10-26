package com.cwc.product_service.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank
        @Size(max = 64)
        String sku,

        @NotBlank
        @Size(max = 200)
        String name,

        @Size(max = 2000)
        String description,

        @NotNull
        @DecimalMin("0.00")
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer quantity

) {}
