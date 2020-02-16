package com.mercadolibre.iptracer.client;

import com.mercadolibre.iptracer.client.model.CountryIpInfoResponse;
import org.springframework.web.client.RestClientException;

public interface CountryIpInfoClient {
   CountryIpInfoResponse getCountryInfoByIp(String ip) throws RestClientException;
}
