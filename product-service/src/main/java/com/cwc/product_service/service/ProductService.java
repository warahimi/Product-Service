package com.cwc.product_service.service;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse getById(Long id);

    Page<ProductResponse> getAll(Pageable pageable);

    ProductResponse update(
            Long id,
            UpdateProductRequest request
    );

    void delete(Long id);
}

