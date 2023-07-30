package com.nth.mike.service.impl;

import com.nth.mike.constant.SortConstant;
import com.nth.mike.entity.Color;
import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductImage;
import com.nth.mike.entity.ProductStatus;
import com.nth.mike.entity.Size;
import com.nth.mike.model.dto.product.ProductFilterDTO;
import com.nth.mike.model.dto.product.ProductFullDetailDTO;
import com.nth.mike.model.mapper.product.ProductFullDetailMapper;
import com.nth.mike.model.request.product.ProductFilterRequest;
import com.nth.mike.repository.ProductDetailRepo;
import com.nth.mike.repository.ProductImageRepo;
import com.nth.mike.repository.ProductRepo;
import com.nth.mike.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductDetailRepo productDetailRepo;
    @Autowired
    private ProductImageRepo productImageRepo;

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findByProductStatus(ProductStatus status) {
        return productRepo.findByProductStatus(status);
    }

    @Override
    public List<Product> findByProductCate(Long productCateId) {
        return null;
    }

    @Override
    public List<Product> findByObjectCate(Long objectCateId) {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            productRepo.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductFilterDTO findByFilter(ProductFilterRequest filter) {
        ProductFilterDTO pfd = new ProductFilterDTO();
        List<Product> productList = productRepo.findByProductStatus(ProductStatus.HIDDEN);

        productList = productListByFilter(filter, productList);

        int sizeList = productList.size();
        int pageSize = filter.getPagination().getLimit() == null ? sizeList : filter.getPagination().getLimit();
        int pageNumber = filter.getPagination().getPage() == null || pageSize >= sizeList ? 1
                : filter.getPagination().getPage();
        int pageCount = sizeList % pageSize == 0 ? sizeList / pageSize : sizeList / pageSize + 1;

        pfd.setPageCount(pageCount);
        pfd.setPageNumber(pageNumber);

        // pagination
        productList = getProductListByPage(filter, productList);

        List<ProductFullDetailDTO> list = new ArrayList<>();
        for (Product p : productList) {
            List<ProductDetail> productDetailList = productDetailRepo.findByProduct(p);
            List<ProductImage> productImageList = productImageRepo.findByProduct(p);
            System.out.println(productImageList.toString());
            list.add(ProductFullDetailMapper.toProductFullDetailDTO(p, productDetailList, productImageList));
        }
        pfd.setListProduct(list);
        return pfd;
    }

    @Override
    public List<ProductFullDetailDTO> findAllProductFullDetail() {
        List<Product> productList = productRepo.findAll();
        List<ProductFullDetailDTO> list = new ArrayList<>();
        for (Product p : productList) {
            List<ProductDetail> productDetailList = productDetailRepo.findByProduct(p);
            List<ProductImage> productImageList = productImageRepo.findByProduct(p);
            list.add(ProductFullDetailMapper.toProductFullDetailDTO(p, productDetailList, productImageList));
        }
        return list;
    }

    @Override
    public ProductFullDetailDTO findProductFullDetailById(Long id) {
        Product product = productRepo.findById(id).orElse(null);
        ProductFullDetailDTO productFullDetailDTO = null;
        if (product != null) {
            List<ProductDetail> productDetailList = productDetailRepo.findByProduct(product);
            List<ProductImage> productImageList = productImageRepo.findByProduct(product);
            productFullDetailDTO = ProductFullDetailMapper.toProductFullDetailDTO(product, productDetailList,
                    productImageList);
        }

        return productFullDetailDTO;
    }

    private List<Product> productListByFilter(ProductFilterRequest filter, List<Product> productList) {
        if (filter.getColorList() != null && !filter.getColorList().isEmpty()) {
            productList = productList.stream()
                    .filter(product -> getColorListForProduct(product, filter.getColorList()).size() > 0)
                    .collect(Collectors.toList());
        }

        if (filter.getSizeList() != null && !filter.getSizeList().isEmpty()) {
            productList = productList.stream()
                    .filter(product -> getSizeListForProduct(product, filter.getSizeList()).size() > 0)
                    .collect(Collectors.toList());
        }

        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            productList = productList.stream()
                    .filter(product -> hasProductDetailWithinPriceRange(product, filter.getMinPrice(),
                            filter.getMaxPrice()))
                    .collect(Collectors.toList());
        }

        if (filter.getProductCategory() != null) {
            productList = productList.stream()
                    .filter(product -> product.getProductCategory().equals(filter.getProductCategory()))
                    .collect(Collectors.toList());
        }

        if (filter.getObjectCategory() != null) {
            productList = productList.stream()
                    .filter(product -> product.getObjectCategory().equals(filter.getObjectCategory()))
                    .collect(Collectors.toList());
        }

        if (filter.getSort() != null && filter.getSort().getSortById() != null) {
            productList
                    .sort(filter.getSort().getSortById().equals(SortConstant.ASC) ? Comparator.comparing(Product::getId)
                            : Comparator.comparing(Product::getId).reversed());
        }

        if (filter.getSort() != null && filter.getSort().getSortByName() != null) {
            productList.sort(
                    filter.getSort().getSortByName().equals(SortConstant.ASC) ? Comparator.comparing(Product::getName)
                            : Comparator.comparing(Product::getName).reversed());
        }

        if (filter.getSort() != null && filter.getSort().getSortByPrice() != null) {
            productList.sort(filter.getSort().getSortByPrice().equals(SortConstant.ASC)
                    ? Comparator.comparingDouble(this::getAverageExportPriceForProduct)
                    : Comparator.comparingDouble(this::getAverageExportPriceForProduct).reversed());
        }

        // sort default
        if (filter.getSort() != null && filter.getSort().getSortByPrice() == null &&
                filter.getSort().getSortByName() == null && filter.getSort().getSortById() == null) {
            productList.sort(Comparator.comparing(Product::getId).reversed());
        }

        return productList;
    }

    private List<Product> getProductListByPage(ProductFilterRequest filter, List<Product> productList) {
        // pagination
        if (filter.getPagination() != null && filter.getPagination().getLimit() != null
                && filter.getPagination().getPage() != null && filter.getPagination().getOffset() != null) {
            int offset = filter.getPagination().getOffset();
            int limit = filter.getPagination().getLimit();
            System.out.println(offset + "-" + limit);
            if (offset <= productList.size() - 1) {
                productList = productList.subList(offset, Math.min(limit + offset, productList.size()));
            }
        }
        return productList;
    }

    private double getAverageExportPriceForProduct(Product product) {
        List<ProductDetail> productDetails = productDetailRepo.findByProduct(product);
        return productDetails.stream()
                .mapToDouble(ProductDetail::getExportPrice)
                .average()
                .orElse(0.0);
    }

    private List<Color> getColorListForProduct(Product product, List<Color> listColor) {
        List<ProductDetail> productDetails = productDetailRepo.findByProduct(product);
        return productDetails.stream()
                .map(ProductDetail::getColor)
                .filter(listColor::contains)
                .collect(Collectors.toList());
    }

    private List<Size> getSizeListForProduct(Product product, List<Size> listSize) {
        List<ProductDetail> productDetails = productDetailRepo.findByProduct(product);
        return productDetails.stream()
                .map(ProductDetail::getSize)
                .filter(listSize::contains)
                .collect(Collectors.toList());
    }

    private boolean hasProductDetailWithinPriceRange(Product product, double minPrice, double maxPrice) {
        List<ProductDetail> productDetails = productDetailRepo.findByProduct(product);
        return productDetails.stream()
                .anyMatch(detail -> detail.getExportPrice() >= minPrice && detail.getExportPrice() <= maxPrice);
    }
}
