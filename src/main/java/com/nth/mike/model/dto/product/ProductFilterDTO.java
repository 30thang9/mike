package com.nth.mike.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {
    private List<ProductFullDetailDTO> listProduct;
    private Integer totalPage;
    private Integer currentPage;
    private Integer totalProduct;

}
