package com.mercadolibre.iptracer.client;

import com.mercadolibre.iptracer.client.model.CurrencyInfoResponse;
import org.springframework.web.client.RestClientException;

public interface CurrencyInfoClient {
    CurrencyInfoResponse getCurrencyInfo(String country) throws RestClientException;
}
