package com.nth.mike.model.request.product;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.ObjectCategory;
import com.nth.mike.entity.ProductCategory;
import com.nth.mike.entity.Size;
import com.nth.mike.model.pagination.Pagination;
import com.nth.mike.model.sort.SortProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductFilterRequest {
    private List<Color> colorList;
    private List<Size> sizeList;
    private ObjectCategory objectCategory;
    private ProductCategory productCategory;
    private Double minPrice;
    private Double maxPrice;
    private SortProduct sort;
    private Pagination pagination;

    public ProductFilterRequest() {
        this.sort = new SortProduct(); // Đặt giá trị mặc định cho sort
        this.pagination = new Pagination();
    }
}
