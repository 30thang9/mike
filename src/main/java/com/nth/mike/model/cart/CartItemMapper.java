package com.nth.mike.model.cart;

import com.nth.mike.entity.*;

public class CartItemMapper {
    public static CartItem toCartItem(OrderDetail od) {
        ProductDetail pd = od.getProductDetail();
        Item item = ItemMapper.toItem(pd);
        CartItem ci = new CartItem();
        ci.setItem(item);
        ci.setPrice(od.getPaymentPrice());
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

    public static OrderDetail toOrderDetail(Order order,CartItem cartItem,ProductDetail pd) {
        OrderDetail orderDetail = new OrderDetail();
        OrderDetailId id = toOrderDetailId(order, cartItem.getItem().getId());
        orderDetail.setId(id);
        orderDetail.setOrder(order);
        orderDetail.setProductDetail(pd);
        orderDetail.setQuantity(cartItem.getQuantity());
        orderDetail.setPaymentPrice(cartItem.getPrice());
        return orderDetail;
    }
}
