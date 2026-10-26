package com.cwc.product_service.repository;

import com.cwc.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);
}
