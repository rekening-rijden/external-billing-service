package com.rekeningrijden.billingservice.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
public class DataPointDTO {
    @Getter @Setter public Date datetime;
    @Getter @Setter public double lng;
    @Getter @Setter public double lat;
    @Getter @Setter public String roadType;
}
