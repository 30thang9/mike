package com.nth.mike.model.request.product;

import com.nth.mike.model.pagination.Pagination;
import com.nth.mike.model.sort.SortProduct;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSearchRequest {
    private String search;
    private SortProduct sort;
    private Pagination pagination;

    public ProductSearchRequest() {
        this.sort = new SortProduct();
        this.pagination = new Pagination();
    }
}
