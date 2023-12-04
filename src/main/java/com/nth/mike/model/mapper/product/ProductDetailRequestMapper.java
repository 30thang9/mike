package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductDetailId;
import com.nth.mike.model.request.product.ProductDetailAddRequest;
import com.nth.mike.model.request.product.ProductDetailRequest;
import com.nth.mike.service.ColorService;
import com.nth.mike.service.ProductService;
import com.nth.mike.service.SizeService;

public class ProductDetailRequestMapper {
    public static ProductDetail toProductDetail(ProductDetailRequest pdr, ProductService ps, ColorService cs,
                                                SizeService ss) {
        ProductDetail pd = new ProductDetail();
        ProductDetailId id = toProductDetailId(pdr);
        pd.setId(id);
        pd.setProduct(ps.findById(id.getProductId()));
        pd.setColor(cs.findById(id.getColorId()));
        pd.setSize(ss.findById(id.getSizeId()));
        pd.setImportPrice(pdr.getImportPrice());
        pd.setExportPrice(pdr.getExportPrice());
        pd.setDiscountPercent(pdr.getDiscountPercent());
        pd.setProductDetailStatus(pdr.getProductDetailStatus());
        return pd;
    }

    public static ProductDetailId toProductDetailId(ProductDetailRequest pdr) {
        ProductDetailId pdi = new ProductDetailId();
        pdi.setProductId(pdr.getProductId());
        pdi.setColorId(pdr.getColorId());
        pdi.setSizeId(pdr.getSizeId());
        return pdi;
    }
}
