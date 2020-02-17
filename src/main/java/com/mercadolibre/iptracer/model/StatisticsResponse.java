package com.mercadolibre.iptracer.model;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsResponse {
    List<StatisticsInfo> statistics;
    Integer average;

    public StatisticsResponse(List<StatisticsInfo> statistics, Integer average) {
        this.statistics = statistics;
        this.average = average;
    }

    public StatisticsResponse() {
    }
}
