package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.model.dto.product.ProductDetailDTO;

public class ProductDetailMapper {
    public static ProductDetailDTO toProductDetailDTO(ProductDetail pd) {
        ProductDetailDTO pdd = new ProductDetailDTO();
        pdd.setColor(pd.getColor());
        pdd.setSize(pd.getSize());
        pdd.setImportPrice(pd.getImportPrice());
        pdd.setExportPrice(pd.getExportPrice());
        pdd.setQuantity(pd.getQuantity());
        return pdd;
    }
}
