package com.mercadolibre.iptracer.client;

import com.mercadolibre.iptracer.client.model.CountryCodeInfoResponse;
import org.springframework.web.client.RestClientException;

public interface CountryCodeInfoClient {
    CountryCodeInfoResponse getCountryInfoByCode(String code) throws RestClientException;
}
