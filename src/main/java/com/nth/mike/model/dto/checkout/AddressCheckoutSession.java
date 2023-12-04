package com.nth.mike.model.dto.checkout;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressCheckoutSession implements Serializable {
    private String fullName;
    private String email;
    private String phoneNumber;
    private Long cityId;
    private Long districtId;
    private Long wardId;
    private String street;
}
