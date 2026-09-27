DO $$
BEGIN
    IF to_regclass('public.payments') IS NULL
       AND to_regclass('public.payment') IS NOT NULL THEN
        ALTER TABLE payment RENAME TO payments;
    END IF;
END
$$;

CREATE TABLE IF NOT EXISTS payments
(
    id                 VARCHAR(255) NOT NULL,
    amount             DECIMAL(38, 2),
    payment_method     VARCHAR(255),
    order_id           VARCHAR(255),
    created_date       TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP(6) WITHOUT TIME ZONE,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);
