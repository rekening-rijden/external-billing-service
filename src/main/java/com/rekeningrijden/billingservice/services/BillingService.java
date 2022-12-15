package com.rekeningrijden.billingservice.services;

import be.woutschoovaerts.mollie.Client;
import be.woutschoovaerts.mollie.ClientBuilder;
import be.woutschoovaerts.mollie.data.common.Amount;
import be.woutschoovaerts.mollie.data.payment.PaymentMethod;
import be.woutschoovaerts.mollie.data.payment.PaymentRequest;
import be.woutschoovaerts.mollie.data.payment.PaymentResponse;
import be.woutschoovaerts.mollie.exception.MollieException;
import com.google.gson.Gson;
import com.rekeningrijden.billingservice.models.DTOs.BillingDataRequestDTO;
import com.rekeningrijden.billingservice.models.DTOs.ExternalResponseDTO;
import com.rekeningrijden.billingservice.models.DTOs.PaymentInfoDTO;
import com.rekeningrijden.billingservice.models.ExternalBilling.ExternalBillingPublisher;
import com.rekeningrijden.billingservice.models.ExternalBilling.ExternalBillingResponseDTO;
import com.rekeningrijden.billingservice.models.ExternalBilling.ExternalDatapointDTO;
import com.rekeningrijden.billingservice.reporitories.BillingRepository;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingService {
    private final BillingRepository billlingRepository;
    private final Client mollieClient;
    private final HttpClient httpClient;
    private final List<PaymentMethod> paymentMethods = List.of(PaymentMethod.IDEAL, PaymentMethod.CREDIT_CARD);
    private ExternalBillingPublisher publisher;

    @Autowired
    public BillingService(BillingRepository billlingRepository, ExternalBillingPublisher publisher) {
        this.billlingRepository = billlingRepository;
        this.httpClient = HttpClient.newBuilder().build();
        this.mollieClient = new ClientBuilder().withApiKey("test_HUS9ADTxAkq5nqAB3RGWxrSaxj55uC").build();
        this.publisher = publisher;
    }

    public String test() {
        // return this.billingRepository.findAll();
        return "test";
    }

    public ResponseEntity<?> createPayment(BillingDataRequestDTO bdr) {
        // Check if paymetInfoDTO is valid
//        if (paymentInfoDTO == null) {
//            return ResponseEntity.badRequest().body("PaymentInfoDTO is null");
//        }
        // Create payment with mollie
        //Calculate price here
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(new Amount("EUR", new BigDecimal("10.00")));
            paymentRequest.setDescription("Test payment");
            paymentRequest.setRedirectUrl(java.util.Optional.of("https://www.google.com"));

            PaymentResponse paymentResponse = this.mollieClient.payments().createPayment(paymentRequest);
            System.out.println(paymentResponse);
            ExternalResponseDTO erd = new ExternalResponseDTO(bdr.getCarId(), Math.random() * (500 - 12) + 12, 12.0, "1+1=2", paymentResponse.getLinks().getCheckout().getHref());
            return ResponseEntity.ok(erd);
        } catch (MollieException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getPaymentById(String paymentId) throws MollieException {
        PaymentResponse paymentResponse = this.mollieClient.payments().getPayment(paymentId);
        return null;
    }

    public ExternalBillingResponseDTO sendExternalBill(List<ExternalDatapointDTO> datapoints) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://billingservice20221212111818.azurewebsites.net/Bill/generatePriceForTrip"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(datapoints)))
                .build();
        System.out.println(gson.toJson(datapoints));
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());
        publisher.sendMessage(gson.fromJson(postResponse.body(), ExternalBillingResponseDTO.class));
        return gson.fromJson(postResponse.body(), ExternalBillingResponseDTO.class);
    }
}
