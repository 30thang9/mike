package com.nth.mike.model.cart;

import com.nth.mike.entity.ProductDetailId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private ProductDetailId id;
    private Integer quantity;
    private Double price;
}
