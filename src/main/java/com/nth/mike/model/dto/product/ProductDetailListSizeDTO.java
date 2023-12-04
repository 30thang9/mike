package com.nth.mike.model.dto.product;

import java.util.List;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.ProductDetailStatus;
import com.nth.mike.entity.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailListSizeDTO {
    private Color color;
    private List<Size> sizes;
    private Double importPrice;
    private Double exportPrice;
    private Integer quantity;
    private Double discountPercent;
    private ProductDetailStatus productDetailStatus;
    private List<String> images;
}
