package com.nabgha.ecommerce.product;

import com.nabgha.ecommerce.exception.BusinessException;
import com.nabgha.ecommerce.exception.ProductServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestClient restClient;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requests) {
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        List<PurchaseResponse> purchasedProducts;
        try {
            purchasedProducts = restClient.post()
                    .uri(productUrl + "/purchase")
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .body(requests)
                    .retrieve()
                    .body(responseType);
        } catch (HttpClientErrorException e) {
            throw new BusinessException("Product purchase rejected: " + e.getStatusText(), e);
        } catch (HttpServerErrorException | ResourceAccessException e) {
            throw new ProductServiceUnavailableException(
                    "Product service is currently unavailable",
                    e
            );
        }

        if (purchasedProducts == null) {
            throw new IllegalStateException("Product service returned an empty purchase response");
        }
        return purchasedProducts;
    }
}
