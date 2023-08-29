package com.nth.mike.model.cart;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductDetailId;
import com.nth.mike.model.mapper.product.ProductDetailMapper;

public class ItemMapper {
    public static Item toItem(ProductDetail pd) {
        Item item = new Item();
        item.setId(pd.getId());
        item.setName(pd.getProduct().getName());
        item.setUrlAvatar(pd.getProduct().getUrlAvatar());
        item.setObjectCategory(pd.getProduct().getObjectCategory());
        item.setProductCategory(pd.getProduct().getProductCategory());
        item.setProductStatus(pd.getProduct().getProductStatus());
        item.setHotStatus(pd.getProduct().getHotStatus());
        item.setBestSellerStatus(pd.getProduct().getBestSellerStatus());
        item.setDiscountStatus(pd.getProduct().getDiscountStatus());
        item.setDiscountPercent(pd.getProduct().getDiscountPercent());
        // item.setImportPrice(pd.getImportPrice());
        // item.setExportPrice(pd.getExportPrice());
        // item.setQuantity(pd.getQuantity());
        item.setItemDetail(ProductDetailMapper.toProductDetailDTO(pd));
        return item;
    }

    public static ProductDetailId toProductDetailId(Item item) {
        ProductDetailId productDetailId = new ProductDetailId();
        productDetailId.setProductId(item.getId().getProductId());
        productDetailId.setColorId(item.getId().getColorId());
        productDetailId.setSizeId(item.getId().getSizeId());
        return productDetailId;
    }
}
