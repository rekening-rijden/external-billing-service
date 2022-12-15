package com.rekeningrijden.billingservice.models.ExternalBilling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExternalDatapointDTO {
    private int carId;
    private ExternalCoordDTO lat_long;
    private String dateTimeStamp;
    private int routeId;
    private int laneMaxSpeedms;
    private String vehicleTypeName;
    private String emissionType;

}
