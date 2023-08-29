package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.ProductImage;
import com.nth.mike.model.dto.product.ProductImageDTO;

public class ProductImageMapper {
    public static ProductImageDTO toProductImageDTO(ProductImage pi) {
        ProductImageDTO pid = new ProductImageDTO();
        pid.setUrlImage(pi.getUrlImage());
        return pid;
    }
}
