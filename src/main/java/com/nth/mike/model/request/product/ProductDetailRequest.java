package com.nth.mike.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Long colorId;
    @NotNull
    private Long sizeId;
    @NotNull
    private Double importPrice;
    @NotNull
    private Double exportPrice;
}
