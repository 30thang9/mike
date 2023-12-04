package com.nth.mike.model.dto.product;

import com.nth.mike.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Color color;
    private Size size;
    private Double importPrice;
    private Double exportPrice;
    private Integer quantity;
    private Double discountPercent;
    private ProductDetailStatus productDetailStatus;
    private List<String> images;
}
