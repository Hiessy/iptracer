package com.mercadolibre.iptracer.service;

import com.mercadolibre.iptracer.EntityManagerFactory;
import com.mercadolibre.iptracer.client.CountryCodeInfoClient;
import com.mercadolibre.iptracer.client.CountryIpInfoClient;
import com.mercadolibre.iptracer.client.CurrencyInfoClient;
import com.mercadolibre.iptracer.client.model.CountryIpInfoResponse;
import com.mercadolibre.iptracer.exceptions.DataAccessException;
import com.mercadolibre.iptracer.exceptions.RestClientException;
import com.mercadolibre.iptracer.model.RequestInfo;
import com.mercadolibre.iptracer.model.StatisticsResponse;
import com.mercadolibre.iptracer.repository.IpTracerRepository;
import com.mercadolibre.iptracer.service.impl.IpTracerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class IpTracerServiceTest {

    @Mock
    private IpTracerRepository repository;
    @Mock
    private CountryIpInfoClient countryIpInfoClient;
    @Mock
    private CountryCodeInfoClient countryCodeInfoClient;
    @Mock
    private CurrencyInfoClient currencyInfoClient;
    @InjectMocks
    private IpTracerServiceImpl ipTracerService;

    private String ip = "13.13.13.13";
    private String country = "Argentina";
    private Integer distance = 450;

    @Test
    public void  logRequestShouldSaveEntity() throws Exception {
        RequestInfo rq = new RequestInfo(ip, country, distance);
        when(countryIpInfoClient.getCountryInfoByIp(any())).thenReturn(EntityManagerFactory.getCountryIpInfoResponse());
        given(repository.findById(any())).willReturn(Optional.empty());
        given(repository.save(any())).willReturn(rq);
        RequestInfo response = ipTracerService.logRequest(ip);
        assertThat(response.getDistance()).isEqualTo(distance);
        assertThat(response.getCount()).isEqualTo(1);
    }
    @Test
    public void  logRequestShouldUpdateCounter() throws Exception {
        RequestInfo rq = new RequestInfo( ip, country, distance);
        when(countryIpInfoClient.getCountryInfoByIp(any())).thenReturn(EntityManagerFactory.getCountryIpInfoResponse());
        given(repository.findById(any())).willReturn(Optional.of(rq));
        given(repository.save(any())).willReturn(rq);
        RequestInfo response = ipTracerService.logRequest(ip);
        assertThat(response.getDistance()).isEqualTo(distance);
        assertThat(response.getCount()).isEqualTo(2);
    }

    @Test(expected = DataAccessException.class)
    public void  logRequestShouldReturnDataAccessException() throws Exception{
        RequestInfo rq = new RequestInfo(ip, country, distance);
        when(countryIpInfoClient.getCountryInfoByIp(any())).thenReturn(EntityManagerFactory.getCountryIpInfoResponse());
        given(repository.findById(any())).willThrow(new DataAccessException("Something unexpected happened see...", HttpStatus.INTERNAL_SERVER_ERROR));
        ipTracerService.logRequest(ip);
    }

    @Test(expected = RestClientException.class)
    public void  logRequestShouldReturnNotFound() throws Exception{
        when(countryIpInfoClient.getCountryInfoByIp(any())).thenReturn(new CountryIpInfoResponse(null,null,null,null,null,null,null));
        ipTracerService.logRequest(ip);
    }

    @Test
    public void shouldReturnAStatisticResponse() {
        Iterable<RequestInfo> response = EntityManagerFactory.getFindAllResponse();
        given(repository.findAll()).willReturn(response);
        List<StatisticsResponse> statistics = ipTracerService.getStatistics();
        assertThat(statistics.isEmpty()).isEqualTo(false);
        assertThat(statistics.size()).isEqualTo(2);
        assertThat(statistics.get(0).getDistance()).isEqualTo(19265);
    }

    @Test
    public void shouldReturnAEmptyStatisticResponse() {
        Iterable<RequestInfo> response = new ArrayList<>();
        given(repository.findAll()).willReturn(response);
        List<StatisticsResponse> statistics = ipTracerService.getStatistics();
        assertThat(statistics.isEmpty()).isEqualTo(false);
        assertThat(statistics.size()).isEqualTo(2);
        assertThat(statistics.get(0).getDistance()).isEqualTo(0);
    }
}
