package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductImage;
import com.nth.mike.model.dto.product.ProductDetailDTO;
import com.nth.mike.model.dto.product.ProductFullDetailDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFullDetailMapper {
        public static ProductFullDetailDTO toProductFullDetailDTO(Product product, List<ProductDetail> productDetail) {
                ProductFullDetailDTO pfdd = new ProductFullDetailDTO();
                pfdd.setProduct(product);

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

        public static ProductFullDetailDTO toProductFullDetailDTO(Product product, List<ProductDetail> productDetail,Double avgRating,Long countEvaluation) {
                 ProductFullDetailDTO pfdd = new ProductFullDetailDTO();
                pfdd.setProduct(product);

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
                pfdd.setAvgRating(avgRating);
                pfdd.setCountEvaluation(countEvaluation);
                
                return pfdd;
        }

}
