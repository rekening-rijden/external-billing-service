package com.rekeningrijden.billingservice.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ExternalResponseDTO {
    @Getter @Setter public int carId;
    @Getter @Setter public double totalPrice;
    @Getter @Setter public double totalDistance;
    @Getter @Setter public String calculation;
    @Getter @Setter public String paymentUrl;
}
