package com.nth.mike.model.dto.product;

import com.nth.mike.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Color color;

    private Size size;

    private Material material;

    private Double importPrice;

    private Double exportPrice;

    private Integer quantity;
}
