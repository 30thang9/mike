package com.nth.mike.controller.api;

import com.nth.mike.constant.StatusConstant;
import com.nth.mike.entity.*;
import com.nth.mike.model.dto.product.ProductFilterDTO;
import com.nth.mike.model.dto.product.ProductFullDetailDTO;
import com.nth.mike.model.mapper.product.ProductAddRequestMapper;
import com.nth.mike.model.mapper.product.ProductDetailAddRequestMapper;
import com.nth.mike.model.mapper.product.ProductDetailRequestMapper;
import com.nth.mike.model.mapper.product.ProductFilterRequestMapper;
import com.nth.mike.model.mapper.product.ProductSearchRequestMapper;
import com.nth.mike.model.request.product.*;
import com.nth.mike.model.response.shared.BasicResponse;
import com.nth.mike.service.*;
import com.nth.mike.utils.UploadUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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
    private UploadUtils uploadUtils;
    @Autowired
    private SizeImageService sizeImageService;

    // ...
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/product-detail-single/{id}")
    public ResponseEntity<ProductDetail> getProductDetail(@PathVariable("id") String id) {
        try {
            String[] s = id.trim().replaceAll(" ", "").split("-");
            if (s.length != 3) {
                return ResponseEntity.badRequest().build();
            }

            List<Long> t = new ArrayList<Long>();
            for (String l : s) {
                t.add(Long.parseLong(l));
            }
            ProductDetailId productDetailId = new ProductDetailId(t.get(0), t.get(1), t.get(2));
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
    public ResponseEntity<ProductFullDetailDTO> getProductColor(@RequestParam("productId") Long productId,
            @RequestParam("colorId") Long colorId) {
        Product product = productService.findById(productId);
        Color color = colorService.findById(colorId);
        return ResponseEntity.ok(productService.findProductFullDetailByProductColor(product, color));
    }

    @GetMapping("/product-size-color-single")
    public ResponseEntity<ProductFullDetailDTO> getProductFullDetailByProductDetail(
            @RequestParam("productId") Long productId, @RequestParam("colorId") Long colorId,
            @RequestParam("sizeId") Long sizeId) {
        ProductDetailId productDetailId = new ProductDetailId(productId, colorId, sizeId);
        return ResponseEntity.ok(
                productService.findProductFullDetailByProductDetail(productDetailService.findById(productDetailId)));
    }

    @GetMapping("/product-filter")
    public ResponseEntity<?> getProductByFilter(
            @RequestParam(required = true) String filter) {
        ProductFilterRequest pfd = ProductFilterRequestMapper
                .requestToProductFilterRequest(filter, colorService, sizeService, objectCategoryService,
                        productCategoryService);
        return ResponseEntity.ok(productService.findByFilter(pfd));
    }

    @GetMapping(value = "/product-search", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ProductFilterDTO> getProductBySearch(
            @RequestParam(required = true) String search) {
        System.out.println(search);
        ProductSearchRequest psr = ProductSearchRequestMapper
                .requestToProductSearchRequest(search, colorService, sizeService,
                        objectCategoryService,
                        productCategoryService);
        return ResponseEntity.ok(productService.findBySearch(psr));
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> addProduct(@ModelAttribute ProductAddFillRequest request) {
        try {
            ProductAddRequest product = request.getProduct();
            List<ProductDetailAddRequest> productDetails = request.getProductDetails();

            if (!ProductDetailAddRequestMapper.isValidProductDetail(productDetails, colorService, sizeService)) {
                return ResponseEntity.badRequest().body("Can't add detail");
            }

            String imageUrl = uploadUtils.saveImage(product.getAvatarFile());
            if (imageUrl == null) {
                return ResponseEntity.internalServerError().body("Failed to upload image");
            }

            product.setAvatar(imageUrl);
            Product productSave = productService.save(ProductAddRequestMapper.toProduct(product, objectCategoryService,
                    productCategoryService, sizeImageService));

            if (productSave == null) {
                return ResponseEntity.internalServerError().body("Failed to add product");
            }

            for (ProductDetailAddRequest pd : productDetails) {
                pd.setProductId(productSave.getId());
                List<ProductDetail> productDetailsList = ProductDetailAddRequestMapper.toProductDetails(pd,
                        productService, colorService, sizeService);

                Set<ProductImage> productImages = new HashSet<>();
                for (MultipartFile imageFile : pd.getImageFiles()) {
                    String name = uploadUtils.saveImage(imageFile);
                    ProductImage productImage = new ProductImage();
                    productImage.setImage(name);
                    productImages.add(productImage);
                }

                for (ProductDetail productDetail : productDetailsList) {
                    productDetail.setProductImages(productImages);
                    productImages = new HashSet<>(productDetailService.save(productDetail).getProductImages());
                }

            }

            return ResponseEntity.ok("Add product successfully!");
        } catch (IOException e) {
            String errorMessage = "Failed to add product: " + e.getMessage();
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }

    // @Async
    // public CompletableFuture<Void> processProductDetail(ProductDetailAddRequest
    // pd, Long productId) throws IOException {
    // pd.setProductId(productId);
    //
    // List<ProductDetail> productDetailsList =
    // ProductDetailAddRequestMapper.toProductDetails(pd, productService,
    // colorService, sizeService);
    //
    // Set<ProductImage> productImages = new HashSet<>();
    // for (MultipartFile imageFile : pd.getImageFiles()) {
    // String name = uploadUtils.saveImage(imageFile);
    // ProductImage productImage = new ProductImage();
    // productImage.setImage(name);
    // productImages.add(productImage);
    // }
    //
    // for (ProductDetail productDetail : productDetailsList) {
    // productDetail.setProductImages(productImages);
    // productImages = new
    // HashSet<>(productDetailService.save(productDetail).getProductImages());
    // }
    //
    // return CompletableFuture.completedFuture(null);
    // }
    //
    // @PostMapping("/add")
    // @Transactional
    // public ResponseEntity<?> addProduct(@ModelAttribute ProductAddFillRequest
    // request) {
    // try {
    // ProductAddRequest product = request.getProduct();
    // List<ProductDetailAddRequest> productDetails = request.getProductDetails();
    //
    // if (!ProductDetailAddRequestMapper.isValidProductDetail(productDetails,
    // colorService, sizeService)) {
    // return ResponseEntity.badRequest().body("Can't add detail");
    // }
    //
    // String imageUrl = uploadUtils.saveImage(product.getAvatarFile());
    // if (imageUrl == null) {
    // return ResponseEntity.internalServerError().body("Failed to upload avatar
    // image");
    // }
    //
    // product.setAvatar(imageUrl);
    // Product productSave =
    // productService.save(ProductAddRequestMapper.toProduct(product,
    // objectCategoryService, productCategoryService, sizeImageService));
    //
    // if (productSave == null) {
    // return ResponseEntity.internalServerError().body("Failed to add product");
    // }
    //
    // Executor executor = Executors.newFixedThreadPool(5);
    //
    // List<CompletableFuture<Void>> futures = new ArrayList<>();
    //
    // for (ProductDetailAddRequest pd : productDetails) {
    // CompletableFuture<Void> future = processProductDetail(pd,
    // productSave.getId()).thenRunAsync(() -> {
    // // Any additional processing after the completion of the asynchronous task
    // }, executor);
    // futures.add(future);
    // }
    //
    // CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new
    // CompletableFuture[0]));
    // allOf.join(); // Block until all tasks are completed
    //
    // return ResponseEntity.ok("Add product successfully!");
    // } catch (IOException e) {
    // String errorMessage = "Failed to add product: " + e.getMessage();
    // return ResponseEntity.internalServerError().body(errorMessage);
    // }
    // }

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
                    product.setAvatar(imageUrl);
                } else {
                    product.setAvatar(uploadUtils.getOriginalFileName(avatar));
                }
            }

            Product updatedProduct = productService.save(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (IOException e) {
            String errorMessage = "Failed to edit product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> deleteProduct(
    // @PathVariable("id") Long id) {
    // try {
    // Product p = productService.findById(id);
    // if (p != null) {
    // Long m = productService.deleteById(id);
    // if (m != 0) {
    // uploadUtils.deleteImage(p.getUrlAvatar());
    // return ResponseEntity.ok(m);
    // }
    // }
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not
    // found.");
    // } catch (IOException e) {
    // String errorMessage = "Failed to delete product: " + e.getMessage();
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    // }
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("id") Long id) {
        Product p = productService.findById(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not found.");
        }
        p.setProductStatus(ProductStatus.INACTIVE);
        try {
            productService.save(p);
            return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Delete is successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Failed to delete product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    // images
    // @PostMapping("/product-image/add")
    // public ResponseEntity<?> addImageProduct(@RequestParam String productId,
    // @RequestParam("listImages") List<MultipartFile> listImages) {
    // try {
    //
    // if (productId == null || productId.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product
    // ID");
    // }
    //
    // Product product = productService.findById(Long.parseLong(productId));
    // if (product == null) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not
    // found");
    // }
    //
    // List<String> imageUrls = new ArrayList<>();
    //
    // for (int i = 0; i < listImages.size(); i++) {
    // MultipartFile imageFile = listImages.get(i);
    // String imageUrl = uploadUtils.saveImage(imageFile);
    // if (imageUrl == null) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed
    // to upload image");
    // }
    // imageUrls.add(imageUrl);
    // }
    //
    // // Save the product images
    // for (String imageUrl : imageUrls) {
    // ProductImage pi = new ProductImage();
    // pi.setUrlImage(imageUrl);
    // pi.setProduct(product);
    // productImageService.save(pi);
    // }
    //
    // return ResponseEntity.ok(new BasicResponse("success", "Add image success"));
    // } catch (IOException e) {
    // String errorMessage = "Failed to add product images: " + e.getMessage();
    // return
    // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    // }
    // }

    // @PutMapping("/product-image/edit/{productId}")
    // public ResponseEntity<?> editImageProduct(@PathVariable("productId") String
    // productId,
    // @RequestParam("listImages") List<MultipartFile> listImages) {
    // try {
    //
    // if (productId == null || productId.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product
    // ID");
    // }
    //
    // Product product = productService.findById(Long.parseLong(productId));
    // if (product == null) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not
    // found");
    // }
    //
    // List<String> fileNames = new ArrayList<>();
    // for (int i = 0; i < listImages.size(); i++) {
    // MultipartFile imageFile = listImages.get(i);
    // String fileName = uploadUtils.getOriginalFileName(imageFile);
    // if (fileName != null) {
    // fileNames.add(fileName);
    // }
    // }
    //
    // List<ProductImage> productImages =
    // productImageService.findByProduct(product);
    //
    // if (productImages.size() != fileNames.size()) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Server not
    // handle");
    // }
    //
    // // Sử dụng HashSet để lưu các giá trị duy nhất của fileNames và productImages
    // Set<String> fileNameSet = new HashSet<>(fileNames);
    // Set<String> productImageUrlSet = productImages.stream()
    // .map(ProductImage::getUrlImage)
    // .collect(Collectors.toSet());
    // // Tìm các phần tử trùng lặp và xóa chúng khỏi fileNames
    // fileNames.removeIf(fileName -> productImageUrlSet.contains(fileName));
    // // Tìm các phần tử trùng lặp và xóa chúng khỏi productImages
    // productImages.removeIf(productImage ->
    // fileNameSet.contains(productImage.getUrlImage()));
    //
    // if (!fileNames.isEmpty() && !productImages.isEmpty()) {
    // List<MultipartFile> listImagesUpdate = new ArrayList<>();
    // for (MultipartFile imageFile : listImages) {
    // String fileName = uploadUtils.getOriginalFileName(imageFile);
    // if (fileNames.contains(fileName)) {
    // listImagesUpdate.add(imageFile);
    // }
    // }
    // for (int i = 0; i < productImages.size(); i++) {
    // MultipartFile imageFile = listImagesUpdate.get(i);
    // String name = uploadUtils.saveImage(imageFile);
    // ProductImage pi = productImages.get(i);
    // productImageService.deleteById(pi.getUrlImage());
    // pi.setUrlImage(name);
    // productImageService.save(pi);
    // }
    //
    // }
    // return ResponseEntity.ok(new BasicResponse("success", "Edit image success"));
    //
    // } catch (IOException e) {
    // String errorMessage = "Failed to add product images: " + e.getMessage();
    // log.error(errorMessage, e);
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    // } catch (NumberFormatException e) {
    // String errorMessage = "Invalid product ID format: " + productId;
    // log.error(errorMessage, e);
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    // } catch (Exception e) {
    // String errorMessage = "An unexpected error occurred: " + e.getMessage();
    // log.error(errorMessage, e);
    // return
    // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    // }
    // }

    // @DeleteMapping("/product-image/delete/{productId}")
    // public ResponseEntity<?> deleteImageProduct(@PathVariable("productId") String
    // productId,
    // @RequestBody ListImageNameRequest listName) {
    // if (productId == null || productId.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product
    // ID");
    // }
    //
    // Product product = productService.findById(Long.parseLong(productId));
    // if (product == null) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    // }
    //
    // List<ProductImage> productImages =
    // productImageService.findByProduct(product);
    // List<String> listImage = listName.getListImage();
    // for (String fileName : listImage) {
    // if (!productImages.stream().anyMatch(image ->
    // image.getUrlImage().contains(fileName))) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Once of list images
    // not found");
    // }
    // }
    //
    // try {
    // for (String fileName : listImage) {
    // productImageService.deleteById(fileName);
    // }
    //
    // return ResponseEntity.ok(new BasicResponse("success", "Edit image success"));
    // } catch (Exception e) {
    // e.printStackTrace();
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete
    // error");
    // }
    //
    // }

    // product detail
    // @GetMapping("/product-detail/list")
    // public ResponseEntity<List<ProductFullDetailDTO>> getProductDetail() {
    // return ResponseEntity.ok(productService.findAllProductFullDetail());
    // }

    @PostMapping("/product-detail/add")
    public ResponseEntity<?> addProductDetails(@RequestBody List<@Valid ProductDetailRequest> productDetails,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Invalid request: " + bindingResult.getFieldError().getDefaultMessage());
        }

        for (ProductDetailRequest productDetail : productDetails) {
            if (productService.findById(productDetail.getProductId()) == null) {
                return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found."));
            }

            if (productDetailService.findById(ProductDetailRequestMapper.toProductDetailId(productDetail)) != null) {
                return ResponseEntity.badRequest()
                        .body(new BasicResponse(StatusConstant.ERROR, "Product detail exists."));
            }

            try {
                ProductDetail pd = ProductDetailRequestMapper.toProductDetail(productDetail, productService,
                        colorService,
                        sizeService);
                pd = productDetailService.save(pd);
                if (pd == null) {
                    return ResponseEntity.internalServerError()
                            .body(new BasicResponse(StatusConstant.ERROR, "Save error."));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError()
                        .body(new BasicResponse(StatusConstant.ERROR, "Save error."));
            }
        }

        return ResponseEntity.ok("Product details added successfully.");
    }

    @PutMapping("/product-detail/edit")
    public ResponseEntity<?> editProductDetails(@RequestBody List<@Valid ProductDetailRequest> productDetails,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Invalid request: " + bindingResult.getFieldError().getDefaultMessage());
        }

        for (ProductDetailRequest productDetail : productDetails) {
            if (productService.findById(productDetail.getProductId()) == null) {
                return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found."));
            }

            if (productDetailService.findById(ProductDetailRequestMapper.toProductDetailId(productDetail)) == null) {
                return ResponseEntity.badRequest()
                        .body(new BasicResponse(StatusConstant.ERROR, "Product detail not exists."));
            }

            try {
                ProductDetail pd = ProductDetailRequestMapper.toProductDetail(productDetail, productService,
                        colorService,
                        sizeService);
                pd = productDetailService.save(pd);
                if (pd == null) {
                    return ResponseEntity.internalServerError()
                            .body(new BasicResponse(StatusConstant.ERROR, "Save error."));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError()
                        .body(new BasicResponse(StatusConstant.ERROR, "Save error."));
            }
        }

        return ResponseEntity.ok("Product details added successfully.");
    }

    @DeleteMapping("/product-detail/delete")
    public ResponseEntity<?> deleteProductDetails(@RequestBody List<@Valid ProductDetailRequest> productDetails,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Invalid request: " + bindingResult.getFieldError().getDefaultMessage());
        }

        for (ProductDetailRequest productDetail : productDetails) {
            if (productService.findById(productDetail.getProductId()) == null) {
                return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found."));
            }

            if (productDetailService.findById(ProductDetailRequestMapper.toProductDetailId(productDetail)) == null) {
                return ResponseEntity.badRequest()
                        .body(new BasicResponse(StatusConstant.ERROR, "Product detail not exists."));
            }

            try {
                productDetailService.deleteById(ProductDetailRequestMapper.toProductDetailId(productDetail));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError()
                        .body(new BasicResponse(StatusConstant.ERROR, "Save error."));
            }
        }

        return ResponseEntity.ok("Product details added successfully.");
    }

}
