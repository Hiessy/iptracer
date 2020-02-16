package com.mercadolibre.iptracer.model;

import com.mercadolibre.iptracer.client.model.Language;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IpTracerResponse {

    private String code;
    private String name;
    private List<Language> languages;
    private Double currency;
    private List<String> timezone;
    private Integer distance;

    public IpTracerResponse(String code, String name, List<Language> languages, Double currency, List<String> timezone, Integer distance) {
        this.code = code;
        this.name = name;
        this.languages = languages;
        this.currency = currency;
        this.timezone = timezone;
        this.distance = distance;
    }
    public IpTracerResponse(String code, String name, List<Language> languages, Double currency){
        this.code = code;
        this.name = name;
        this.languages = languages;
        this.currency = currency;
        this.timezone = new ArrayList<>();
        this.distance = 0;
    }

}


