package com.nth.mike.model.response.privated.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailErrorResponse {
    private String status;
    private List<ProductDetailNameResponse> message;
}
