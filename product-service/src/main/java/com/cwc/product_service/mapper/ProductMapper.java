package com.cwc.product_service.mapper;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.entity.Product;

public final class ProductMapper {

    private ProductMapper() {
        // Utility class
    }

    public static Product toEntity(ProductRequest request) {
        return new Product(
                request.sku(),
                request.name(),
                request.description(),
                request.price(),
                request.quantity()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.isActive(),
                product.getVersion(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
