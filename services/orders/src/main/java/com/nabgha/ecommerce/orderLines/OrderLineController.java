package com.nabgha.ecommerce.orderLines;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orderlines")
@RequiredArgsConstructor
class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderId(
            @PathVariable("orderId") String orderId
    ){
        return ResponseEntity.ok(orderLineService.findByOrderId(orderId));
    }
}
