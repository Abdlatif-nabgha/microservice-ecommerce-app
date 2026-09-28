package com.nabgha.ecommerce.category;


import lombok.Builder;

@Builder
public record CategoryResponse(
        String id,
        String name,
        String description
) {}
