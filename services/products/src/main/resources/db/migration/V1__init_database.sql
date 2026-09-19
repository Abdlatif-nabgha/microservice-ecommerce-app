CREATE TABLE category
(
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id                 VARCHAR(255)     NOT NULL,
    name               VARCHAR(255),
    description        VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price              DECIMAL,
    category_id        VARCHAR(255),
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);