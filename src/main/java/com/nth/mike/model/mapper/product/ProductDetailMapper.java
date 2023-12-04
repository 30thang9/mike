package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductImage;
import com.nth.mike.model.dto.product.ProductDetailDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailMapper {
    public static ProductDetailDTO toProductDetailDTO(ProductDetail pd) {
        ProductDetailDTO pdd = new ProductDetailDTO();
        pdd.setColor(pd.getColor());
        pdd.setSize(pd.getSize());
        pdd.setImportPrice(pd.getImportPrice());
        pdd.setExportPrice(pd.getExportPrice());
        pdd.setQuantity(pd.getQuantity());
        pdd.setDiscountPercent(pd.getDiscountPercent());
        pdd.setProductDetailStatus(pd.getProductDetailStatus());
        List<String> images = pd.getProductImages().stream()
                .map(ProductImage::getImage)
                .collect(Collectors.toList());
        pdd.setImages(images);
        return pdd;
    }
}
