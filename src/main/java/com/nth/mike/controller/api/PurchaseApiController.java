package com.nth.mike.controller.api;

import com.nth.mike.constant.StatusConstant;
import com.nth.mike.entity.*;
import com.nth.mike.model.dto.purchase.PurchaseDTO;
import com.nth.mike.model.mapper.purchase.PurchaseSaveMapper;
import com.nth.mike.model.request.purchase.PurchaseDetailRequest;
import com.nth.mike.model.request.purchase.PurchaseRequest;
import com.nth.mike.model.request.purchase.PurchaseSaveRequest;
import com.nth.mike.model.response.shared.BasicResponse;
import com.nth.mike.model.response.purchase.ProductDetailNameResponse;
import com.nth.mike.service.ProductDetailService;
import com.nth.mike.service.PurchaseDetailService;
import com.nth.mike.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/mike/api/purchase")
public class PurchaseApiController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDetailService pds;
    @Autowired
    private ProductDetailService prod;
    @Autowired
    private PurchaseSaveMapper purchaseSaveMapper;

    @GetMapping("/list")
    public ResponseEntity<List<PurchaseDTO>> getPurchases() {
        return ResponseEntity.ok(purchaseService.findAll());
    }

    @GetMapping("/purchase-single/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.findById(id));
    }

    @GetMapping("/purchase-detail/list")
    public ResponseEntity<List<PurchaseDetail>> getPurchaseDetails() {
        return ResponseEntity.ok(pds.findAll());
    }

    @GetMapping("/purchase-detail/purchase-detail-single/{purchaseId}")
    public ResponseEntity<List<PurchaseDetail>> getPurchaseDetail(@PathVariable Long purchaseId) {
        Purchase purchase = purchaseService.findById(purchaseId);
        return ResponseEntity.ok(pds.findByPurchase(purchase));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPurchase(@RequestBody PurchaseSaveRequest request) {
        PurchaseRequest pr = request.getPurchase();
        List<PurchaseDetailRequest> pdrs = request.getPurchaseDetail();
        if (pr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "Purchase is null"));
        }
        if (pdrs == null || pdrs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "PurchaseDetails is null or empty"));
        }

        List<ProductDetailNameResponse> listError = purchaseSaveMapper.productDetailIdNotFound(request);
        if (!listError.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "Some product detail is not found"));
        }

        Purchase p = purchaseSaveMapper.toPurchase(request);

        try {
            p = purchaseService.save(p);
            if (p != null) {
                List<PurchaseDetail> list = purchaseSaveMapper.toPurchaseDetail(request, p);
                for (PurchaseDetail ld : list) {
                    System.out.println(ld.toString());
                    pds.save(ld);
                }
            }
            return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Ok"));
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse(StatusConstant.ERROR, e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/edit/{purchaseId}")
    public ResponseEntity<?> editPurchase(@PathVariable String purchaseId, @RequestBody PurchaseSaveRequest request) {
        PurchaseRequest pr = request.getPurchase();
        Purchase purchase = purchaseService.findById(Long.parseLong(purchaseId));
        List<PurchaseDetailRequest> pdrs = request.getPurchaseDetail();
        if (pr == null || purchaseId == null || purchaseId.equals("") || purchase == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "Purchase is null"));
        }
        if (pdrs == null || pdrs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "PurchaseDetails is null or empty"));
        }

        List<ProductDetailNameResponse> listError = purchaseSaveMapper.productDetailIdNotFound(request);
        if (!listError.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "Some product detail is not found"));
        }

        Purchase p = purchaseSaveMapper.toPurchase(request);
        p.setId(purchase.getId());

        try {
            p = purchaseService.save(p);
            if (p != null) {
                List<PurchaseDetail> lists = purchaseSaveMapper.toPurchaseDetail(request, p);
                List<PurchaseDetail> oldPurchaseDetails = pds.findByPurchase(p);

                Set<PurchaseDetailId> oldPurchaseDetailIds = oldPurchaseDetails.stream()
                        .map(PurchaseDetail::getId)
                        .collect(Collectors.toSet());
                for (PurchaseDetailId id : oldPurchaseDetailIds) {
                    pds.deleteById(id);
                }

                for (PurchaseDetail purchaseDetail : lists) {
                    pds.save(purchaseDetail);
                }
            }
            return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Ok"));
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse(StatusConstant.ERROR, e.getMessage()));
            throw new RuntimeException(e);
        }
    }

}
