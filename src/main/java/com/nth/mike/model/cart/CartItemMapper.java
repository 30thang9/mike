package com.nth.mike.model.cart;

import com.nth.mike.entity.*;

public class CartItemMapper {
    public static CartItem toCartItem(OrderDetail od) {
        ProductDetail pd = od.getProductDetail();
        Item item = ItemMapper.toItem(pd);
        CartItem ci = new CartItem();
        ci.setItem(item);
        ci.setPrice(od.getOrderPrice());
        ci.setQuantity(od.getQuantity());
        return ci;
    }

    public static OrderDetailId toOrderDetailId(Order order, ProductDetailId pdi) {
        OrderDetailId orderId = new OrderDetailId();
        orderId.setOrderId(order.getId());
        orderId.setProductId(pdi.getProductId());
        orderId.setSizeId(pdi.getSizeId());
        orderId.setColorId(pdi.getColorId());
        return orderId;
    }
}
