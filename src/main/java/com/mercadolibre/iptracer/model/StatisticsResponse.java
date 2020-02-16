package com.mercadolibre.iptracer.model;

import lombok.Data;

@Data
public class StatisticsResponse {
    private String countryName;
    private Integer distance;
    private Integer invocations;

    public StatisticsResponse(String countryName, Integer distance, Integer invocations) {
        this.countryName = countryName;
        this.distance = distance;
        this.invocations = invocations;
    }

    public StatisticsResponse() {
    }
}
