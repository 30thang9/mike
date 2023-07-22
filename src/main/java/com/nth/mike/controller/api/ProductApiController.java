package com.nth.mike.controller.api;

import com.nth.mike.entity.*;
import com.nth.mike.model.dto.ProductFilterDTO;
import com.nth.mike.model.dto.ProductFullDetailDTO;
import com.nth.mike.model.mapper.ProductFilterMapper;
import com.nth.mike.model.pagination.ProductFilterPaging;
import com.nth.mike.service.*;
import com.nth.mike.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mike/api/product")
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

    // ...
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/product-single/{id}")
    public ResponseEntity<ProductFullDetailDTO> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findProductFullDetailById(id));
    }

    @GetMapping("/product-filter")
    public ResponseEntity<ProductFilterPaging> getProductByFilter(
            @RequestParam(required = false) String filter
    ) {
        ProductFilterDTO pfd = ProductFilterMapper
                .requestToProductFilterDTO(filter,colorService,sizeService,objectCategoryService,productCategoryService);
        return ResponseEntity.ok(productService.findByFilter(pfd));
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @ModelAttribute Product product,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        try {
            if (avatar != null && !avatar.isEmpty()) {
                String imageUrl = uploadUtils.saveImage(avatar);
//                String g=uploadUtils.getOriginalFileName(avatar);
                if (imageUrl != null) {
                    product.setUrlAvatar(imageUrl);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
                }
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
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        try {
            if (avatar != null && !avatar.isEmpty()) {
                String imageUrl=null;
//                System.out.println(uploadUtils.getOriginalFileName(avatar));
                System.out.println(avatar.getName());
                if(uploadUtils.isImageExists(uploadUtils.getOriginalFileName(avatar))){
                    imageUrl=uploadUtils.getRelativeImagePath(uploadUtils.getOriginalFileName(avatar));
                }else {
                    Product p=productService.findById(id);
                    System.out.println(p.getUrlAvatar());
                    uploadUtils.deleteImage(uploadUtils.getFileNameByPath(p.getUrlAvatar()));
                    imageUrl= uploadUtils.saveImage(avatar);
                }
                if (imageUrl != null) {
                    product.setUrlAvatar(imageUrl);
//                    System.out.println(imageUrl);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
                }
            }
            Product find = productService.findById(id);
            if (find != null) {
                Product updatedProduct = productService.save(product);
                return ResponseEntity.ok(updatedProduct);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (IOException e) {
            String errorMessage = "Failed to edit product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("id") Long id
    ) {
        try {
            Product p=productService.findById(id);
            if(p!=null){
                Long m = productService.deleteById(id);
                if (m!=0){
                    uploadUtils.deleteImage(uploadUtils.getFileNameByPath(p.getUrlAvatar()));
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
    public ResponseEntity<List<ProductFullDetailDTO>> getProductDetail(){
        return ResponseEntity.ok(productService.findAllProductFullDetail());
    }


}
