package com.rekeningrijden.billingservice.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

public class BillingDataRequestDTO {

    @Getter @Setter public int carId;
    @Getter @Setter public RouteDTO route;
}
