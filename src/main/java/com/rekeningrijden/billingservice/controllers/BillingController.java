package com.rekeningrijden.billingservice.controllers;

import be.woutschoovaerts.mollie.exception.MollieException;
import com.rekeningrijden.billingservice.models.DTOs.BillingDataRequestDTO;
import com.rekeningrijden.billingservice.models.DTOs.PaymentInfoDTO;
import com.rekeningrijden.billingservice.services.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/billing")
@CrossOrigin(origins = "*")
public class BillingController {
    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/test")
    public String test() {
        return this.billingService.test();
    }

    @PostMapping("/externalBilling")
    public ResponseEntity<?> createPayment(@RequestBody BillingDataRequestDTO billingDataRequestDTO) {
        return this.billingService.createPayment(billingDataRequestDTO);
    }

    @GetMapping("/getpayment/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable String paymentId) throws MollieException {
        return this.billingService.getPaymentById(paymentId);
    }
}
