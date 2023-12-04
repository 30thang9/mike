package com.nth.mike.model.cart;

import com.nth.mike.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Item item;
    private Integer quantity;
    private Double price;
    public Double getTotal(){
        return quantity * price;
    }
    public Boolean isValidItem(){
        return quantity <= item.getItemDetail().getQuantity()
                && !item.getProductStatus().equals(ProductStatus.INACTIVE)
                && item.getItemDetail().getQuantity() > 0;
    }
}
