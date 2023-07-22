package com.nth.mike.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenAuthResponse {
    private String accessToken;
    private String refreshToken;
}
