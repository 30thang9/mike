package com.nth.mike.model.request.product;

import com.nth.mike.entity.ProductDetailStatus;
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
     @NotNull
     private Double discountPercent;
    @NotNull
    private ProductDetailStatus productDetailStatus;

}
