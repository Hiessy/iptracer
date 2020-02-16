package com.mercadolibre.iptracer.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryIpInfoResponse {
    private String ip;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_name")
    private String countryName;
    private String city;
    private Double latitude;
    private Double longitude;
    private Location location;

    public CountryIpInfoResponse(String ip, String countryCode, String countryName, String city, Double latitude, Double longitude, Location location) {
        this.ip = ip;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public CountryIpInfoResponse() {
    }
}