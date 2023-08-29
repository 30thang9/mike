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

        public static ProductFullDetailDTO toProductFullDetailDTO(Product product, List<ProductDetail> productDetail,
                        List<ProductImage> productImage) {
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

                // List<ProductImageDTO> listImage = productImage.stream()
                // .map(ProductImageMapper::toProductImageDTO)
                // .collect(Collectors.toList());

                List<String> listImage = productImage.stream()
                                .map(ProductImage::getUrlImage).collect(Collectors.toList());

                pfdd.setMinPrice(minPrice);
                pfdd.setMaxPrice(maxPrice);
                pfdd.setProductDetail(list);
                pfdd.setProductImage(listImage);

                return pfdd;
        }

}
