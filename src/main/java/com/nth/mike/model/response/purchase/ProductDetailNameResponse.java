package com.nth.mike.model.response.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailNameResponse {
    private String productName;
    private String colorName;
    private String sizeName;
}
