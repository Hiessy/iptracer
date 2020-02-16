package com.mercadolibre.iptracer.client.impl;

import com.mercadolibre.iptracer.client.CountryCodeInfoClient;
import com.mercadolibre.iptracer.client.model.CountryCodeInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class CountryCodeInfoClientImpl implements CountryCodeInfoClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${client.rest.countries.url}")
    private String getCountryUrl;

    @Override
    public CountryCodeInfoResponse getCountryInfoByCode(String code) throws RestClientException {
        String url = getCountryUrl+code;
        log.info("Looking up country code info using url" + url);
        try {
            ResponseEntity<CountryCodeInfoResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CountryCodeInfoResponse>(){});
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
