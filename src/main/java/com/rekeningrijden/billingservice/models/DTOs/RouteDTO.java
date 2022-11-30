package com.rekeningrijden.billingservice.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {
    @Getter @Setter public ArrayList<DataPointDTO> coordinates;
    @Getter @Setter public String carType;
}
