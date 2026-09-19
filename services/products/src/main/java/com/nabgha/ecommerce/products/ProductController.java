package com.nabgha.ecommerce.products;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody @Valid ProductRequest request
    ){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> requests
    ){
        return ResponseEntity.ok(productService.purchaseProducts(requests));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("productId") String productId
    ){
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }
}
