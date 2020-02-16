package com.mercadolibre.iptracer.client.impl;

import com.mercadolibre.iptracer.client.CountryIpInfoClient;
import com.mercadolibre.iptracer.client.model.CountryIpInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class CountryIpInfoClientImpl implements CountryIpInfoClient{

    private RestTemplate restTemplate;

    public CountryIpInfoClientImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Value("${client.country.url}")
    private String getCountryUrl;

    @Value("${client.country.key}")
    private String getCountryKey;

    @Override
    public CountryIpInfoResponse getCountryInfoByIp(String ip) throws RestClientException {
        String url = getCountryUrl+ip+getCountryKey;
        log.info("Looking up country info using url" + url);
        try {
            ResponseEntity<CountryIpInfoResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CountryIpInfoResponse>(){});
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            String errorMsg = "Client error status code: " + ex.getStatusText() + ". " + ex.getMessage();
            log.error(errorMsg, ex);
            throw new RestClientException(errorMsg, ex);
        } catch (HttpServerErrorException ex) {
            String errorMsg = " Server error status code: " + ex.getStatusText() + ". " + ex.getMessage();
            log.error(errorMsg, ex);
            throw new RestClientException(errorMsg, ex);
        } catch(org.springframework.web.client.RestClientException ex) {
            String errorMsg = " Unknown error. " + ex.getMessage();
            log.error(errorMsg, ex);
            throw new RestClientException(errorMsg, ex);
        } catch(Exception ex) {
            String errorMsg = " Unknown error. " + ex.getMessage();
            log.error(errorMsg, ex);
            throw new RestClientException(errorMsg, ex);
        }
    }
}

