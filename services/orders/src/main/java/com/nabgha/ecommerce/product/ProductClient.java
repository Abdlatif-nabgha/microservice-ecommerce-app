package com.nabgha.ecommerce.product;

import com.nabgha.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        ResponseEntity<List<PurchaseResponse>> responseEntity = restClient.post()
                .uri(productUrl + "/purchase")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(requests)
                .retrieve()
                .toEntity(responseType);

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException(responseEntity.getStatusCode().toString());
        }
        return responseEntity.getBody();
    }
}
