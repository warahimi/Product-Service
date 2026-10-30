package com.cwc.product_service.service;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.dto.UpdateProductRequest;
import com.cwc.product_service.entity.Product;
import com.cwc.product_service.exception.ProductConflictException;
import com.cwc.product_service.exception.ProductNotFoundException;
import com.cwc.product_service.mapper.ProductMapper;
import com.cwc.product_service.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor injection
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request)
    {
        if (productRepository.existsBySku(request.sku())) {
            throw new ProductConflictException(
                    "Product SKU already exists: " + request.sku()
            );
        }

        Product product = ProductMapper.toEntity(request);

        try {
            Product saved = productRepository.saveAndFlush(product);
            return ProductMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ProductConflictException(
                    "Product could not be created due to a " +
                            "database constraint conflict"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = findProduct(id);
        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id,UpdateProductRequest request)
    {
        Product product = findProduct(id);

        if (!Objects.equals(
                product.getVersion(),
                request.version()
        )) {
            throw new ProductConflictException(
                    "Product was modified by another request. " +
                            "Reload the product and retry."
            );
        }

        // We only allow an SKU already owned by this product.
        if (!product.getSku().equals(request.sku())
                && productRepository.existsBySku(request.sku())) {

            throw new ProductConflictException(
                    "SKU already exists: " + request.sku()
            );
        }

        product.update(
                request.sku(),
                request.name(),
                request.description(),
                request.price(),
                request.quantity(),
                request.active()
        );

        try {
            Product saved = productRepository.saveAndFlush(product);
            return ProductMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ProductConflictException(
                    "Product update violates a database constraint"
            );
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException(id)
                );
    }
}
