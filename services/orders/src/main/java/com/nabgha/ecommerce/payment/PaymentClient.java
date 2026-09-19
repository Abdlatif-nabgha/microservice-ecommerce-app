package com.nabgha.ecommerce.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "product-service",
        url = "${application.config.product-url}"
)
public interface PaymentClient {

    @PostMapping
    Integer requestOrderPayment(
            @RequestBody PaymentRequest request
    );
}
