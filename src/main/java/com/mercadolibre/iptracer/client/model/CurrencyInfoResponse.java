package com.mercadolibre.iptracer.client.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class CurrencyInfoResponse {
    private HashMap<String, Double> rates;

    public CurrencyInfoResponse() {
    }

    public CurrencyInfoResponse(HashMap<String, Double> rates) {
        this.rates = rates;
    }
}