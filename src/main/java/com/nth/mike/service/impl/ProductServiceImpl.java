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
import com.nth.mike.model.dto.product.ProductPagingDTO;
import com.nth.mike.model.mapper.product.ProductFullDetailMapper;
import com.nth.mike.model.pagination.Pagination;
import com.nth.mike.model.request.product.ProductFilterRequest;
import com.nth.mike.model.request.product.ProductSearchRequest;
import com.nth.mike.repository.ProductDetailRepo;
import com.nth.mike.repository.ProductImageRepo;
import com.nth.mike.repository.ProductRepo;
import com.nth.mike.service.ProductService;
import com.nth.mike.utils.ConvertUTF8Utils;

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
        if (filter != null) {
            List<Product> productList = productRepo.findByProductStatus(ProductStatus.ACTIVE);

            productList = productListByFilter(filter, productList);

            // pagination
            ProductPagingDTO ppd = getProductListByPage(filter.getPagination(), productList);
            productList = ppd.getListProduct();
            pfd.setTotalPage(ppd.getTotalPage());
            pfd.setCurrentPage(ppd.getCurrentPage());
            pfd.setTotalProduct(ppd.getTotalProduct());

            List<ProductFullDetailDTO> list = new ArrayList<>();
            for (Product p : productList) {
                List<ProductDetail> productDetailList = productDetailRepo.findByProduct(p);
                list.add(ProductFullDetailMapper.toProductFullDetailDTO(p, productDetailList));
            }
            pfd.setListProduct(list);
        } else {
            pfd.setTotalPage(0);
            pfd.setCurrentPage(0);
            pfd.setTotalProduct(0);
            pfd.setListProduct(new ArrayList<>());
        }
        return pfd;
    }

    @Override
    public ProductFilterDTO findBySearch(ProductSearchRequest filter) {
        ProductFilterDTO pfd = new ProductFilterDTO();
        if (filter != null) {
            List<Product> productList = productRepo.findByProductStatus(ProductStatus.ACTIVE);

            productList = productListBySearch(filter, productList);

            // pagination
            ProductPagingDTO ppd = getProductListByPage(filter.getPagination(), productList);
            productList = ppd.getListProduct();
            pfd.setTotalPage(ppd.getTotalPage());
            pfd.setCurrentPage(ppd.getCurrentPage());
            pfd.setTotalProduct(ppd.getTotalProduct());

            List<ProductFullDetailDTO> list = new ArrayList<>();
            for (Product p : productList) {
                List<ProductDetail> productDetailList = productDetailRepo.findByProduct(p);
                list.add(ProductFullDetailMapper.toProductFullDetailDTO(p, productDetailList));
            }
            pfd.setListProduct(list);
        } else {
            pfd.setTotalPage(0);
            pfd.setCurrentPage(0);
            pfd.setTotalProduct(0);
            pfd.setListProduct(new ArrayList<>());
        }
        return pfd;
    }

    // @Override
    // public List<ProductFullDetailDTO> findAllProductFullDetail() {
    // List<Product> productList = productRepo.findAll();
    // List<ProductFullDetailDTO> list = new ArrayList<>();
    // for (Product p : productList) {
    // List<ProductDetail> productDetailList = productDetailRepo.findByProduct(p);
    // List<ProductImage> productImageList = productImageRepo.findByProduct(p);
    // list.add(ProductFullDetailMapper.toProductFullDetailDTO(p, productDetailList,
    // productImageList));
    // }
    // return list;
    // }

    @Override
    public ProductFullDetailDTO findProductFullDetailByProduct(Product product) {
        ProductFullDetailDTO productFullDetailDTO = new ProductFullDetailDTO();
        if (product != null) {
            List<ProductDetail> productDetailList = productDetailRepo.findByProduct(product);
            productFullDetailDTO = ProductFullDetailMapper.toProductFullDetailDTO(product, productDetailList);
        }

        return productFullDetailDTO;
    }

    @Override
    public ProductFullDetailDTO findProductFullDetailByProductColor(Product product, Color color) {
        ProductFullDetailDTO productFullDetailDTO = new ProductFullDetailDTO();
        if (product != null && color != null) {
            List<ProductDetail> productDetailList = productDetailRepo.findByProductColor(product, color);
            productFullDetailDTO = ProductFullDetailMapper.toProductFullDetailDTO(product, productDetailList);
        }

        return productFullDetailDTO;
    }

    @Override
    public ProductFullDetailDTO findProductFullDetailByProductDetail(ProductDetail productDetail) {
        ProductFullDetailDTO productFullDetailDTO = new ProductFullDetailDTO();
        if (productDetail != null) {
            List<ProductDetail> productDetailList = new ArrayList<>();
            productDetailList.add(productDetail);
            productFullDetailDTO = ProductFullDetailMapper.toProductFullDetailDTO(productDetail.getProduct(),
                    productDetailList);
        }

        return productFullDetailDTO;
    }

    private List<Product> productListBySearch(ProductSearchRequest search, List<Product> productList) {
        if (search.getSearch() != null && !search.equals("")) {
            System.out.println(search.getSearch());
            productList = productList.stream()
                    .filter(product -> ConvertUTF8Utils.convert(product.getName()).toLowerCase()
                            .contains(ConvertUTF8Utils.convert(search.getSearch().toLowerCase()))

                            || ConvertUTF8Utils.convert(product.getObjectCategory().getName()).toLowerCase()
                                    .contains(ConvertUTF8Utils.convert(search.getSearch().toLowerCase()))

                            || ConvertUTF8Utils.convert(product.getProductCategory().getName()).toLowerCase()
                                    .contains(ConvertUTF8Utils.convert(search.getSearch().toLowerCase())))
                    .collect(Collectors.toList());

            if (search.getSort() != null && search.getSort().getSortById() != null) {
                productList
                        .sort(search.getSort().getSortById().equals(SortConstant.ASC)
                                ? Comparator.comparing(Product::getId)
                                : Comparator.comparing(Product::getId).reversed());
            }

            if (search.getSort() != null && search.getSort().getSortByName() != null) {
                productList.sort(
                        search.getSort().getSortByName().equals(SortConstant.ASC)
                                ? Comparator.comparing(Product::getName)
                                : Comparator.comparing(Product::getName).reversed());
            }

            if (search.getSort() != null && search.getSort().getSortByPrice() != null) {
                productList.sort(search.getSort().getSortByPrice().equals(SortConstant.ASC)
                        ? Comparator.comparingDouble(this::getAverageExportPriceForProduct)
                        : Comparator.comparingDouble(this::getAverageExportPriceForProduct).reversed());
            }

            // sort default
            if (search.getSort() != null && search.getSort().getSortByPrice() == null &&
                    search.getSort().getSortByName() == null && search.getSort().getSortById() == null) {
                productList.sort(Comparator.comparing(Product::getId).reversed());
            }

            return productList;
        } else {
            return new ArrayList<>();
        }
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
                    .sort(filter.getSort().getSortById().equals(SortConstant.ASC)
                            ? Comparator.comparing(Product::getId)
                            : Comparator.comparing(Product::getId).reversed());
        }

        if (filter.getSort() != null && filter.getSort().getSortByName() != null) {
            productList.sort(
                    filter.getSort().getSortByName().equals(SortConstant.ASC)
                            ? Comparator.comparing(Product::getName)
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

    private ProductPagingDTO getProductListByPage(Pagination pagination, List<Product> productList) {
        ProductPagingDTO ppd = new ProductPagingDTO();
        if (pagination.getLimit() != null
                && pagination.getPage() != null && pagination.getOffset() != null) {
            ppd.setTotalProduct(productList.size());
            if (productList.isEmpty()) {
                ppd.setListProduct(productList);
                ppd.setTotalPage(0);
            } else {
                int productListSize = productList.size();
                int offset = pagination.getOffset();
                int limit = pagination.getLimit();
                if (offset <= productListSize - 1) {
                    productList = productList.subList(offset, Math.min(limit + offset, productListSize));
                } else {
                    productList = new ArrayList<>();
                }
                ppd.setListProduct(productList);
                Integer totalPage = productListSize % limit == 0 ? productListSize / limit
                        : productListSize / limit + 1;
                ppd.setTotalPage(totalPage);
            }
            ppd.setCurrentPage(pagination.getPage());
            return ppd;
        }
        ppd.setListProduct(productList);
        ppd.setTotalPage(1);
        ppd.setCurrentPage(1);
        return ppd;
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
