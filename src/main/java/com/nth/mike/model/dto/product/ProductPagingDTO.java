package com.nth.mike.model.dto.product;

import java.util.List;

import com.nth.mike.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPagingDTO {
    private List<Product> listProduct;
    private Integer pageCount;
    private Integer pageNumber;
}
