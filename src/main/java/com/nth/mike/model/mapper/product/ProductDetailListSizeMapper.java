package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductImage;
import com.nth.mike.entity.Size;
import com.nth.mike.model.dto.product.ProductDetailListSizeDTO;
import com.nth.mike.model.dto.product.ProductDetailDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailListSizeMapper {

    public static ProductDetailListSizeDTO toProductDetailListSizeDTO(List<ProductDetail> pds) {
        ProductDetailListSizeDTO pdd = new ProductDetailListSizeDTO();
        if (!pds.isEmpty()) {
            ProductDetail pd = pds.get(0);
            pdd.setColor(pd.getColor());
            pdd.setImportPrice(pd.getImportPrice());
            pdd.setExportPrice(pd.getExportPrice());
            pdd.setQuantity(pd.getQuantity());
            pdd.setDiscountPercent(pd.getDiscountPercent());
            pdd.setProductDetailStatus(pd.getProductDetailStatus());
            List<String> images = pd.getProductImages().stream()
                    .map(ProductImage::getImage)
                    .collect(Collectors.toList());
            pdd.setImages(images);
            List<Size> sizes = new ArrayList<>();
            for (ProductDetail p : pds) {
                sizes.add(p.getSize());
            }
            pdd.setSizes(sizes);
        }
        return pdd;
    }

    public static ProductDetailListSizeDTO toProductDetailListSizeDTOFromProductDetailDTO(List<ProductDetailDTO> pds) {
        ProductDetailListSizeDTO pdd = new ProductDetailListSizeDTO();
        if (!pds.isEmpty()) {
            ProductDetailDTO pd = pds.get(0);
            pdd.setColor(pd.getColor());
            pdd.setImportPrice(pd.getImportPrice());
            pdd.setExportPrice(pd.getExportPrice());
            pdd.setQuantity(pd.getQuantity());
            pdd.setDiscountPercent(pd.getDiscountPercent());
            pdd.setProductDetailStatus(pd.getProductDetailStatus());
            pdd.setImages(pd.getImages());
            List<Size> sizes = new ArrayList<>();
            for (ProductDetailDTO p : pds) {
                sizes.add(p.getSize());
            }
            pdd.setSizes(sizes);
        }
        return pdd;
    }
}
