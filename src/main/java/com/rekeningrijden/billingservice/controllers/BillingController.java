package com.rekeningrijden.billingservice.controllers;

import be.woutschoovaerts.mollie.exception.MollieException;
import com.rekeningrijden.billingservice.models.DTOs.BillingDataRequestDTO;
import com.rekeningrijden.billingservice.models.DTOs.PaymentInfoDTO;
import com.rekeningrijden.billingservice.models.ExternalBilling.ExternalDatapointDTO;
import com.rekeningrijden.billingservice.services.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/sendExternalBill")
    public ResponseEntity<?> sendBill(@RequestBody List<ExternalDatapointDTO> datapoints) throws IOException, InterruptedException {
        return new ResponseEntity<>(this.billingService.sendExternalBill(datapoints), HttpStatus.OK);
    }

    @GetMapping("/getpayment/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable String paymentId) throws MollieException {
        return this.billingService.getPaymentById(paymentId);
    }
}
