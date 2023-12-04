package com.nth.mike.model.request.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressCheckoutRequest {
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Long cityId;
    @NotNull
    private Long districtId;
    @NotNull
    private Long wardId;
    @NotNull
    private String address;
}
