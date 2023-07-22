package com.nth.mike.model.response.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private String status;
    public  String message;
}
