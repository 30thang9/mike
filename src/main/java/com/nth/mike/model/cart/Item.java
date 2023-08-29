package com.nth.mike.model.cart;

import com.nth.mike.entity.BestSellerStatus;
import com.nth.mike.entity.DiscountStatus;
import com.nth.mike.entity.HotStatus;
import com.nth.mike.entity.ObjectCategory;
import com.nth.mike.entity.ProductCategory;
import com.nth.mike.entity.ProductDetailId;
import com.nth.mike.entity.ProductStatus;
import com.nth.mike.model.dto.product.ProductDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private ProductDetailId id;
    private String name;
    private String urlAvatar;
    private ObjectCategory objectCategory;
    private ProductCategory productCategory;
    private ProductStatus productStatus;
    private HotStatus hotStatus;
    private BestSellerStatus bestSellerStatus;
    private DiscountStatus discountStatus;
    private Double discountPercent;
    // private Double importPrice;
    // private Double exportPrice;
    // private Integer quantity;
    private ProductDetailDTO itemDetail;
}
