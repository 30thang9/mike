package com.nth.mike.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailAddRequest {
    private Long productId;
    @NotNull
    private Long colorId;
    @NotEmpty
    private List<Long> sizeIds;
    @NotNull
    private Double importPrice;
    @NotNull
    private Double exportPrice;
    @NotNull
    private Double discountPercent;
    @NotEmpty
    private List<MultipartFile> imageFiles;
    private List<String> images;
    @NotNull
    private String productDetailStatus;
}
