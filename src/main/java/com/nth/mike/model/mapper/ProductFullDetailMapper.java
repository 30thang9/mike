package com.nth.mike.model.mapper;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.Role;
import com.nth.mike.model.dto.ProductDetailDTO;
import com.nth.mike.model.dto.ProductFullDetailDTO;
import com.nth.mike.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFullDetailMapper {
    public static ProductFullDetailDTO toProductFullDetailDTO(Product product, List<ProductDetail> productDetail) {
        ProductFullDetailDTO pfdd = new ProductFullDetailDTO();
        pfdd.setProduct(product);

        // Sử dụng Comparator để tìm giá trị tối thiểu và tối đa của exportPrice
        Double minPrice = productDetail.stream()
                .map(ProductDetail::getExportPrice)
                .min(Comparator.comparingDouble(Double::doubleValue))
                .orElse(0.0);

        Double maxPrice = productDetail.stream()
                .map(ProductDetail::getExportPrice)
                .max(Comparator.comparingDouble(Double::doubleValue))
                .orElse(0.0);

        List<ProductDetailDTO> list = productDetail.stream()
                .map(ProductDetailMapper::toProductDetailDTO)
                .collect(Collectors.toList());

        pfdd.setMinPrice(minPrice);
        pfdd.setMaxPrice(maxPrice);
        pfdd.setProductDetail(list);

        return pfdd;
    }

}
