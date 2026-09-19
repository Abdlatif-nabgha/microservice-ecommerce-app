package com.nabgha.ecommerce.products;

import com.nabgha.ecommerce.exceptions.ProductNotFoundException;
import com.nabgha.ecommerce.exceptions.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(ProductRequest request) {

        return productRepository.save(mapper.toProduct(request));
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requests) {
        var productIds = requests
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        // 1. Fetch all requested products from the database in one query
        var savedProducts = productRepository.findAllByIdInOrderById(productIds);

        // 2. Validate if all requested products exist
        if (savedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products don't exist");
        }

        // 3. Create a map for fast O(1) lookup of requested quantities
        var requestQuantityMap = requests.stream()
                .collect(Collectors.toMap(
                        ProductPurchaseRequest::productId,
                        ProductPurchaseRequest::quantity,
                        Double::sum // If same product requested multiple times, sum them
                ));

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        // 4. Process each product
        for (Product product : savedProducts) {
            double requestedQuantity = requestQuantityMap.get(product.getId());

            // Validate stock
            if (product.getAvailableQuantity() < requestedQuantity) {
                throw new ProductPurchaseException("Insufficient stock for product: " + product.getId());
            }

            // Deduct stock
            product.setAvailableQuantity(product.getAvailableQuantity() - requestedQuantity);

            purchasedProducts.add(new ProductPurchaseResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    requestedQuantity
            ));
        }

        // 5. Save all updated products back to the database in a single batch operation (performance optimal)
        productRepository.saveAll(savedProducts);

        return purchasedProducts;
    }

    public ProductResponse findById(String productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        var savedProduct = productRepository.save(product);

        return mapper.toProductResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
