package com.nth.mike.model.dto.product;

import com.nth.mike.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFullDetailDTO {
    private Product product;
    private List<ProductDetailDTO> productDetail;
    private List<String> productImage;
    private Double minPrice;
    private Double maxPrice;
}
