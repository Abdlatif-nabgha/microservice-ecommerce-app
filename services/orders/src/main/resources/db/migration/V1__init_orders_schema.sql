CREATE TABLE orders
(
    id                 VARCHAR(255) NOT NULL,
    reference          VARCHAR(255),
    total_amount       DECIMAL(12, 2),
    payment_method     VARCHAR(50),
    customer_id        VARCHAR(255),
    created_date       TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE orderlines
(
    id         VARCHAR(255) NOT NULL,
    product_id VARCHAR(255),
    order_id   VARCHAR(255) NOT NULL,
    quantity   DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_orderlines PRIMARY KEY (id)
);

ALTER TABLE orderlines
    ADD CONSTRAINT FK_ORDERLINES_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);
