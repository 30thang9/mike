package com.nth.mike.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressRequest {
    @NotNull
    private String username;
    @NotNull
    private String fullName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private Long cityId;
    @NotNull
    private Long districtId;
    @NotNull
    private Long wardId;
    @NotNull
    private String street;
    @NotNull
    private Boolean addressDefault;
}
