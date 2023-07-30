package com.nth.mike.model.mapper.purchase;

import com.nth.mike.entity.*;
import com.nth.mike.model.request.purchase.PurchaseDetailRequest;
import com.nth.mike.model.request.purchase.PurchaseSaveRequest;
import com.nth.mike.model.response.purchase.ProductDetailNameResponse;
import com.nth.mike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseSaveMapper {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private MaterialService materialService;

    public Purchase toPurchase(PurchaseSaveRequest psr) {
        try {
            Purchase purchase = new Purchase();
            purchase.setSupplier(supplierService.findById(psr.getPurchase().getSupplierId()));
            purchase.setEmployee(employeeService.findById(psr.getPurchase().getEmployeeId()));
            purchase.setPaymentMethod(psr.getPurchase().getPaymentMethod());
            purchase.setPurchaseDate(psr.getPurchase().getPurchaseDate());
            purchase.setTotalAmount(psr.getPurchase().getTotalPrice());
            return purchase;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<PurchaseDetail> toPurchaseDetail(PurchaseSaveRequest psr, Purchase p) {
        try {
            List<PurchaseDetail> pds = new ArrayList<>();
            for (PurchaseDetailRequest detailRequest : psr.getPurchaseDetail()) {
                PurchaseDetailId purchaseDetailId = new PurchaseDetailId(
                        p.getId(),
                        detailRequest.getProductId(),
                        detailRequest.getColorId(),
                        detailRequest.getSizeId(),
                        detailRequest.getMaterialId());
                ProductDetailId productDetailId = new ProductDetailId(
                        detailRequest.getProductId(),
                        detailRequest.getColorId(),
                        detailRequest.getSizeId(),
                        detailRequest.getMaterialId());

                PurchaseDetail pd = new PurchaseDetail();
                pd.setId(purchaseDetailId);
                pd.setPurchase(p);
                pd.setProductDetail(productDetailService.findById(productDetailId));
                pd.setQuantity(detailRequest.getQuantity());
                pd.setPaymentPrice(detailRequest.getPaymentPrice());

                pds.add(pd);
            }
            return pds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductDetailNameResponse> productDetailIdNotFound(PurchaseSaveRequest psr) {
        try {
            List<ProductDetailNameResponse> productDetailErrors = new ArrayList<>();
            for (PurchaseDetailRequest detailRequest : psr.getPurchaseDetail()) {
                ProductDetailId productDetailId = new ProductDetailId(
                        detailRequest.getProductId(),
                        detailRequest.getColorId(),
                        detailRequest.getSizeId(),
                        detailRequest.getMaterialId());

                if (productDetailService.findById(productDetailId) == null) {
                    ProductDetailNameResponse productDetailError = new ProductDetailNameResponse();
                    productDetailError
                            .setProductName(productService.findById(productDetailId.getProductId()).getName());
                    productDetailError.setColorName(colorService.findById(productDetailId.getColorId()).getName());
                    productDetailError.setSizeName(sizeService.findById(productDetailId.getSizeId()).getName());
                    productDetailError
                            .setMaterialName(materialService.findById(productDetailId.getMaterialId()).getName());

                    productDetailErrors.add(productDetailError);
                }
            }
            return productDetailErrors;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
