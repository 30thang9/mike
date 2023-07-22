package com.nth.mike.model.pagination;

import com.nth.mike.model.dto.ProductFullDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterPaging {
    private List<ProductFullDetailDTO> listProduct;
    private Integer pageCount;
    private Integer pageNumber;
}
