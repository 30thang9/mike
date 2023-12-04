package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.*;
import com.nth.mike.model.request.product.ProductDetailAddRequest;
import com.nth.mike.service.ColorService;
import com.nth.mike.service.ProductService;
import com.nth.mike.service.SizeService;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAddRequestMapper {
    public static List<ProductDetail> toProductDetails(ProductDetailAddRequest pdar, ProductService ps, ColorService cs, SizeService ss) {
        List<ProductDetail> productDetails = new ArrayList<>();
        Product product = ps.findById(pdar.getProductId());
        Color color = cs.findById(pdar.getColorId());
        ProductDetailStatus status = ProductDetailStatus.valueOf(pdar.getProductDetailStatus());
        double importPrice  = pdar.getImportPrice();
        double exportPrice = pdar.getExportPrice();
        double discountPercent = pdar.getDiscountPercent();
        for (Long sizeId : pdar.getSizeIds()) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProduct(product);
            productDetail.setColor(color);
            productDetail.setProductDetailStatus(status);
            productDetail.setImportPrice(importPrice);
            productDetail.setExportPrice(exportPrice);
            productDetail.setDiscountPercent(discountPercent);

            ProductDetailId productDetailId = new ProductDetailId(pdar.getProductId(), pdar.getColorId(), sizeId);
            productDetail.setId(productDetailId);

            productDetail.setSize(ss.findById(sizeId));

            productDetails.add(productDetail);

        }
        return productDetails;
    }

    public static boolean isValidProductDetail(List<ProductDetailAddRequest> list, ColorService cs, SizeService ss) {
        List<Long> validColorIds = getColorIds(cs);
        List<Long> validSizeIds = getSizeIds(ss);

        for (ProductDetailAddRequest p : list) {
            if (!validColorIds.contains(p.getColorId())) {
                return false;
            }
            for (Long id : p.getSizeIds()) {
                if (!validSizeIds.contains(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Long> getColorIds(ColorService cs) {
        List<Long> ids = new ArrayList<>();
        for (Color color : cs.findAll()) {
            ids.add(color.getId());
        }
        return ids;
    }

    private static List<Long> getSizeIds(SizeService ss) {
        List<Long> ids = new ArrayList<>();
        for (Size size : ss.findAll()) {
            ids.add(size.getId());
        }
        return ids;
    }
}
