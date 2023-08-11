package com.nth.mike.controller.api;

import com.nth.mike.entity.*;
import com.nth.mike.model.dto.product.ProductFilterDTO;
import com.nth.mike.model.dto.product.ProductFullDetailDTO;
import com.nth.mike.model.mapper.product.ProductFilterRequestMapper;
import com.nth.mike.model.mapper.product.ProductSearchRequestMapper;
import com.nth.mike.model.request.product.ProductFilterRequest;
import com.nth.mike.model.request.product.ProductSearchRequest;
import com.nth.mike.model.response.shared.BasicResponse;
import com.nth.mike.service.*;
import com.nth.mike.utils.UploadUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mike/api/product")
@Slf4j
public class ProductApiController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ObjectCategoryService objectCategoryService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private UploadUtils uploadUtils;

    // ...
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/product-detail-single/{id}")
    public ResponseEntity<ProductDetail> getProductDetail(@PathVariable("id") String id) {
        try {
            String[] s = id.trim().replaceAll(" ", "").split("-");
            if (s.length != 4) {
                return ResponseEntity.badRequest().build();
            }

            List<Long> t = new ArrayList<Long>();
            for (String l : s) {
                t.add(Long.parseLong(l));
            }
            ProductDetailId productDetailId = new ProductDetailId(t.get(0), t.get(1), t.get(2), t.get(3));
            ProductDetail productDetail = productDetailService.findById(productDetailId);

            if (productDetail == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(productDetail);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/product-single/{productId}")
    public ResponseEntity<ProductFullDetailDTO> getProduct(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        return ResponseEntity.ok(productService.findProductFullDetailByProduct(product));
    }

    @GetMapping("/product-color-single")
    public ResponseEntity<ProductFullDetailDTO> getProductSize(@RequestParam("productId") Long productId,
            @RequestParam("colorId") Long colorId) {
        Product product = productService.findById(productId);
        Color color = colorService.findById(colorId);
        return ResponseEntity.ok(productService.findProductFullDetailByProductColor(product, color));
    }

    @GetMapping("/product-filter")
    public ResponseEntity<ProductFilterDTO> getProductByFilter(
            @RequestParam(required = true) String filter) {
        ProductFilterRequest pfd = ProductFilterRequestMapper
                .requestToProductFilterRequest(filter, colorService, sizeService, objectCategoryService,
                        productCategoryService);
        return ResponseEntity.ok(productService.findByFilter(pfd));
    }

    @GetMapping("/product-search")
    public ResponseEntity<ProductFilterDTO> getProductBySearch(
            @RequestParam(required = true) String search) {
        ProductSearchRequest psr = ProductSearchRequestMapper
                .requestToProductSearchRequest(search, colorService, sizeService, objectCategoryService,
                        productCategoryService);
        return ResponseEntity.ok(productService.findBySearch(psr));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @ModelAttribute Product product,
            @RequestParam("avatar") MultipartFile avatar) {
        try {
            if (avatar != null && !avatar.isEmpty()) {
                String imageUrl = uploadUtils.saveImage(avatar);
                if (imageUrl == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
                }
                product.setUrlAvatar(imageUrl);
            }
            Product savedProduct = productService.save(product);
            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            String errorMessage = "Failed to add product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProduct(
            @PathVariable("id") Long id,
            @ModelAttribute Product product,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        try {
            Product find = productService.findById(id);
            if (find == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            if (avatar != null && !avatar.isEmpty()) {
                String originalFileName = uploadUtils.getOriginalFileName(avatar);
                if (!uploadUtils.isImageExists(originalFileName)) {
                    String imageUrl = uploadUtils.saveImage(avatar);
                    if (imageUrl == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");

                    }
                    product.setUrlAvatar(imageUrl);
                } else {
                    product.setUrlAvatar(uploadUtils.getOriginalFileName(avatar));
                }
            }

            Product updatedProduct = productService.save(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (IOException e) {
            String errorMessage = "Failed to edit product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("id") Long id) {
        try {
            Product p = productService.findById(id);
            if (p != null) {
                Long m = productService.deleteById(id);
                if (m != 0) {
                    uploadUtils.deleteImage(p.getUrlAvatar());
                    return ResponseEntity.ok(m);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not found.");
        } catch (IOException e) {
            String errorMessage = "Failed to delete product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @GetMapping("/product-detail/list")
    public ResponseEntity<List<ProductFullDetailDTO>> getProductDetail() {
        return ResponseEntity.ok(productService.findAllProductFullDetail());
    }

    // images
    @PostMapping("/product-image/add")
    public ResponseEntity<?> addImageProduct(@RequestParam String productId,
            @RequestParam("listImages") List<MultipartFile> listImages) {
        try {

            if (productId == null || productId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID");
            }

            Product product = productService.findById(Long.parseLong(productId));
            if (product == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");
            }

            List<String> imageUrls = new ArrayList<>();

            for (int i = 0; i < listImages.size(); i++) {
                MultipartFile imageFile = listImages.get(i);
                String imageUrl = uploadUtils.saveImage(imageFile);
                if (imageUrl == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
                }
                imageUrls.add(imageUrl);
            }

            // Save the product images
            for (String imageUrl : imageUrls) {
                ProductImage pi = new ProductImage();
                pi.setUrlImage(imageUrl);
                pi.setProduct(product);
                productImageService.save(pi);
            }

            return ResponseEntity.ok(new BasicResponse("success", "Add image success"));
        } catch (IOException e) {
            String errorMessage = "Failed to add product images: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @PutMapping("/product-image/edit/{productId}")
    public ResponseEntity<?> editImageProduct(@PathVariable("productId") String productId,
            @RequestParam("listImages") List<MultipartFile> listImages) {
        try {

            if (productId == null || productId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID");
            }

            Product product = productService.findById(Long.parseLong(productId));
            if (product == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");
            }

            List<String> fileNames = new ArrayList<>();
            for (int i = 0; i < listImages.size(); i++) {
                MultipartFile imageFile = listImages.get(i);
                String fileName = uploadUtils.getOriginalFileName(imageFile);
                if (fileName != null) {
                    fileNames.add(fileName);
                }
            }

            List<ProductImage> productImages = productImageService.findByProduct(product);

            // Sử dụng HashSet để lưu các giá trị duy nhất của fileNames và productImages
            Set<String> fileNameSet = new HashSet<>(fileNames);
            Set<String> productImageUrlSet = productImages.stream()
                    .map(ProductImage::getUrlImage)
                    .collect(Collectors.toSet());

            // Tìm các phần tử trùng lặp và xóa chúng khỏi fileNames
            fileNames.removeIf(fileName -> productImageUrlSet.contains(fileName));

            // Tìm các phần tử trùng lặp và xóa chúng khỏi productImages
            productImages.removeIf(productImage -> fileNameSet.contains(productImage.getUrlImage()));

            if (!fileNames.isEmpty() || !productImages.isEmpty()) {
                if (fileNames.isEmpty()) {
                    for (ProductImage productImage : productImages) {
                        try {
                            uploadUtils.deleteImage(productImage.getUrlImage());
                            productImageService.deleteByUrlImage(productImage.getUrlImage());
                        } catch (Exception e) {
                            // Log the error and continue processing other images
                            log.error("Error deleting image: " + productImage.getUrlImage(), e);
                        }
                    }
                } else if (!fileNames.isEmpty() && !productImages.isEmpty()) {
                    List<ProductImage> updatedProductImages = new ArrayList<>();
                    List<String> addImages = new ArrayList<>();
                    for (int i = 0; i < fileNames.size(); i++) {
                        for (int j = 0; j < listImages.size(); j++) {
                            MultipartFile imageFile = listImages.get(j);
                            String fileName = uploadUtils.getOriginalFileName(imageFile);
                            if (fileName.equals(fileNames.get(i))) {
                                String name = uploadUtils.saveImage(imageFile);
                                if (i < productImages.size()) {
                                    ProductImage updatedProductImage = productImages.get(i);
                                    updatedProductImage.setUrlImage(name);
                                    updatedProductImages.add(updatedProductImage);
                                } else {
                                    addImages.add(name);
                                }
                                break;
                            }
                        }
                    }

                    // Save the updated product images
                    for (ProductImage updatedProductImage : updatedProductImages) {
                        try {
                            productImageService.save(updatedProductImage);
                        } catch (Exception e) {
                            // Log the error and continue processing other images
                            log.error("Error saving image: " + updatedProductImage.getUrlImage(), e);
                        }
                    }
                    if (updatedProductImages.size() < productImages.size()) {
                        List<ProductImage> deletedProductImages = new ArrayList<>(productImages);

                        Set<Long> updatedProductImageIdSet = updatedProductImages.stream()
                                .map(ProductImage::getId)
                                .collect(Collectors.toSet());

                        // Tìm các phần tử trùng lặp và xóa chúng khỏi fileNames
                        deletedProductImages
                                .removeIf(productImage -> updatedProductImageIdSet.contains(productImage.getId()));

                        // Handle the failed updates (e.g., retry or log errors)
                        for (ProductImage deletedProductImage : deletedProductImages) {
                            productImageService.deleteById(deletedProductImage.getId());
                        }
                    }
                    if (!addImages.isEmpty()) {
                        for (String addImage : addImages) {
                            ProductImage pi = new ProductImage();
                            pi.setProduct(product);
                            pi.setUrlImage(addImage);
                            productImageService.save(pi);
                        }

                    }

                } else if (productImages.isEmpty()) {
                    List<ProductImage> updatedProductImages = new ArrayList<>();
                    for (int i = 0; i < fileNames.size(); i++) {
                        for (int j = 0; j < listImages.size(); j++) {
                            MultipartFile imageFile = listImages.get(j);
                            String fileName = uploadUtils.getOriginalFileName(imageFile);
                            if (fileName.equals(fileNames.get(i))) {
                                String name = uploadUtils.saveImage(imageFile);
                                ProductImage updatedProductImage = new ProductImage();
                                updatedProductImage.setUrlImage(name);
                                updatedProductImage.setProduct(product);
                                updatedProductImages.add(updatedProductImage);
                                break;
                            }
                        }
                    }
                    for (ProductImage updatedProductImage : updatedProductImages) {
                        try {
                            productImageService.save(updatedProductImage);
                        } catch (Exception e) {
                            // Log the error and continue processing other images
                            log.error("Error saving image: " + updatedProductImage.getUrlImage(), e);
                        }
                    }
                }
            }
            return ResponseEntity.ok(new BasicResponse("success", "Edit image success"));
        } catch (IOException e) {
            String errorMessage = "Failed to add product images: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (NumberFormatException e) {
            String errorMessage = "Invalid product ID format: " + productId;
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "An unexpected error occurred: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
