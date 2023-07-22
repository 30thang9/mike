package com.nth.mike.model.mapper;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Material;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.Size;
import com.nth.mike.model.dto.ProductDetailDTO;

public class ProductDetailMapper {
    public static ProductDetailDTO toProductDetailDTO(ProductDetail pd){
        ProductDetailDTO pdd=new ProductDetailDTO();
        pdd.setColor(pd.getColor());
        pdd.setSize(pd.getSize());
        pdd.setMaterial(pd.getMaterial());
        pdd.setImportPrice(pd.getImportPrice());
        pdd.setExportPrice(pd.getExportPrice());
        pdd.setQuantity(pd.getQuantity());
        return pdd;
    }
}
