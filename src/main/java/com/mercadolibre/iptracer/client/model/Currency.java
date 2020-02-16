package com.mercadolibre.iptracer.client.model;

import lombok.Data;

@Data
public class Currency {
    private String code;

    public Currency() {
    }

    public Currency(String code) {
        this.code = code;
    }
}
