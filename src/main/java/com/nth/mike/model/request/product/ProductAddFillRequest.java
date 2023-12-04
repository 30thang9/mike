package com.nth.mike.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddFillRequest {
    @NotNull
    private ProductAddRequest product;
    @NotEmpty
    private List<ProductDetailAddRequest> productDetails;
}
