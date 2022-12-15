package com.rekeningrijden.billingservice.models.ExternalBilling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ExternalBillingResponseDTO {
    private String paymentLink;
    private double price;
    private String description;
}
