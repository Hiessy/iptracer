package com.mercadolibre.iptracer.client.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CountryCodeInfoResponse {
    private String name;
    private String alpha2Code;
    private String alpha3Code;
    private List < String > timezones = new ArrayList < > ();
    private List< Currency > currencies = new ArrayList < > ();

    public CountryCodeInfoResponse() {
    }

    public CountryCodeInfoResponse(String name, String alpha2Code, String alpha3Code, List<String> timezones, List<Currency> currencies) {
        this.name = name;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.timezones = timezones;
        this.currencies = currencies;
    }
}