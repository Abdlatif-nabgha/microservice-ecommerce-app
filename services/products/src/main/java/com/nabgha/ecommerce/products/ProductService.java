package com.nabgha.ecommerce.products;

import com.nabgha.ecommerce.category.CategoryRepository;
import com.nabgha.ecommerce.exceptions.CategoryNotFoundException;
import com.nabgha.ecommerce.exceptions.ProductNotFoundException;
import com.nabgha.ecommerce.exceptions.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.nabgha.ecommerce.products.ProductSpecifications.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        return mapper
                .toProductResponse(productRepository
                        .save(mapper.toProduct(request, category))
                );
    }

    @Transactional
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requests) {
        // Sorted, de-duplicated ids with summed quantities
        var requestedQuantities = requests.stream()
                .collect(Collectors.toMap(
                        ProductPurchaseRequest::productId,
                        ProductPurchaseRequest::quantity,
                        Double::sum,
                        TreeMap::new
                ));

        // Query also returns products ordered by id
        var savedProducts = productRepository
                .findAllByIds(requestedQuantities.keySet());

        if (savedProducts.size() != requestedQuantities.size()) {
            throw new ProductPurchaseException("One or more products don't exist");
        }

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (Product product : savedProducts) {
            double quantity = requestedQuantities.get(product.getId());
            if (product.getAvailableQuantity() < quantity) {
                throw new ProductPurchaseException("Insufficient stock for product: \" + product.getId()");
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
            purchasedProducts.add(ProductPurchaseResponse.builder()
                            .productId(product.getId())
                            .name(product.getName())
                            .quantity(quantity)
                            .price(product.getPrice())
                            .description(product.getDescription())
                            .build());
        }
        return purchasedProducts;
    }

    public ProductResponse findById(String productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public Page<ProductResponse> findAll(String categoryId,
                                         BigDecimal min,
                                         BigDecimal max,
                                         Pageable pageable) {
        Specification<Product> spec = hasCategory(categoryId)
                .and(priceAtLeast(min))
                .and(priceAtMost(max));

        return productRepository.findAll(spec, pageable)
                .map(mapper::toProductResponse);
    }


}
