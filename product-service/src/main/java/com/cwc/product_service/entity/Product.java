package com.cwc.product_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 64
    )
    private String sku;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private boolean active = true;

    // Optimistic locking for concurrent updates
    @Version
    @Column(nullable = false)
    private Long version;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Product() {
        // Required by JPA
    }

    public Product(
            String sku,
            String name,
            String description,
            BigDecimal price,
            Integer quantity
    ) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public void update(
            String sku,
            String name,
            String description,
            BigDecimal price,
            Integer quantity,
            boolean active
    ) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public boolean isActive() { return active; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}

