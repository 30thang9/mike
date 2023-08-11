package com.nth.mike.model.cart;

import java.util.ArrayList;
import java.util.List;

import com.nth.mike.entity.ProductDetailId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartOrder {
    private List<CartItem> cart;

    public CartOrder() {
        this.cart = new ArrayList<CartItem>();
    }

    public CartItem getCartItemById(ProductDetailId id) {
        for (CartItem cartItem : cart) {
            if (cartItem.getItem().getId().equals(id)) {
                return cartItem;
            }
        }
        return null;
    }

    public Integer getQuantityById(ProductDetailId id) {
        CartItem cartItem = getCartItemById(id);
        if (cartItem == null) {
            return 0;
        } else {
            return cartItem.getQuantity();
        }
    }

    public void save(CartItem cartItem) {
        CartItem existCartItem = getCartItemById(cartItem.getItem().getId());
        if (existCartItem == null) {
            cart.add(cartItem);
        } else {
            Integer oldQuantity = existCartItem.getQuantity();
            existCartItem.setQuantity(oldQuantity + cartItem.getQuantity());
        }
    }

    public void update(CartItem cartItem) {
        CartItem existCartItem = getCartItemById(cartItem.getItem().getId());
        if (existCartItem != null) {
            existCartItem.setQuantity(cartItem.getQuantity());
            existCartItem.setPrice(cartItem.getPrice());
        }
    }

    public void delete(ProductDetailId id) {
        CartItem existCartItem = getCartItemById(id);
        if (existCartItem != null) {
            cart.remove(existCartItem);
        }
    }

    public Integer getTotalQuantity() {
        Integer totalQuantity = 0;
        for (CartItem cartItem : cart) {
            if (cartItem != null) {
                totalQuantity += cartItem.getQuantity();
            } else {
                System.out.println("Found null CartItem in the cart list!");
            }
        }
        return totalQuantity;
    }

    public Double getTotalAmount() {
        Double totalAmount = 0.0;
        for (CartItem cartItem : cart) {
            if (cartItem != null) {
                totalAmount += cartItem.getQuantity() * cartItem.getPrice();
            } else {
                System.out.println("Found null CartItem in the cart list!");
            }
        }
        return totalAmount;
    }

}
