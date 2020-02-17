package com.mercadolibre.iptracer.model;

import lombok.Data;

@Data
public class StatisticsInfo {
    private String countryName;
    private Integer distance;
    private Integer invocations;

    public StatisticsInfo(String countryName, Integer distance, Integer invocations) {
        this.countryName = countryName;
        this.distance = distance;
        this.invocations = invocations;
    }

    public StatisticsInfo() {
    }
}
