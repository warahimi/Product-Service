
CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,

    sku VARCHAR(64) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(2000),

    price DECIMAL(12, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT NOT NULL DEFAULT 0,

    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT uk_products_sku UNIQUE (sku),

    CONSTRAINT chk_products_price
        CHECK (price >= 0),

    CONSTRAINT chk_products_quantity
        CHECK (quantity >= 0),

    INDEX idx_products_active_id (active, id)
) ENGINE=InnoDB;
