package com.nth.mike.model.sort;

import com.nth.mike.constant.SortConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortProduct {
    private String sortByName;
    private String sortByPrice;
    private String sortById;
}
