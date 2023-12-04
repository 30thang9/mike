package com.nth.mike.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddRequest {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private MultipartFile avatarFile;
    //    @NotNull
    private String avatar;
    @NotNull
    private Long objectCategoryId;
    @NotNull
    private Long productCategoryId;
    @NotNull
    private Long sizeImageId;
    @NotNull
    private String productStatus;
    @NotNull
    private String hotStatus;
    @NotNull
    private String bestSellerStatus;
    @NotNull
    private Double discountPercent;
}
