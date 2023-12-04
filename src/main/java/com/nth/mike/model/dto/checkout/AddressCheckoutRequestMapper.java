package com.nth.mike.model.dto.checkout;

import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderStatus;
import com.nth.mike.model.request.checkout.AddressCheckoutRequest;

public class AddressCheckoutRequestMapper {
    public static Order toOrder(Order order, AddressCheckoutRequest request) {
        order.setFullName(request.getFullName());
        order.setEmail(request.getEmail());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setAddress(request.getAddress());
        order.setOrderStatus(OrderStatus.HANDLE_PAYMENT);
        return order;
    }

    public static AddressCheckoutSession toAddressId(AddressCheckoutRequest request) {
        AddressCheckoutSession addressId = new AddressCheckoutSession();
        addressId.setFullName(request.getFullName());
        addressId.setEmail(request.getEmail());
        addressId.setPhoneNumber(request.getPhoneNumber());
        addressId.setCityId(request.getCityId());
        addressId.setDistrictId(request.getDistrictId());
        addressId.setWardId(request.getWardId());
        addressId.setStreet(request.getAddress().split("\\|")[0]);
        return addressId;
    }
}
