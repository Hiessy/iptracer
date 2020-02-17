package com.mercadolibre.iptracer.controller;

import com.mercadolibre.iptracer.EntityManagerFactory;
import com.mercadolibre.iptracer.exceptions.RestClientException;
import com.mercadolibre.iptracer.service.IpTracerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IpTracerControllerTest {

    @MockBean
    private IpTracerService ipTracerService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void healthCheckReturnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v1/api/healthCheck").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Application is running!")));
    }

    @Test
    public void getIpAddressInformationReturnsOK() throws Exception {
        String ip = "123.123.123.123";
        when(ipTracerService.logRequest(any())).thenReturn(EntityManagerFactory.getInfoRequest());
        when(ipTracerService.buildResponse(any())).thenReturn(EntityManagerFactory.getIpTracerResponse());

        mvc.perform(MockMvcRequestBuilders.get("/v1/api/information?ip="+ip).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("AR"))
                .andExpect(jsonPath("$.name").value("Argentina"))
                .andExpect(jsonPath("$.languages.[0].name").value("Español"))
                .andExpect(jsonPath("$.timezone.[0]").value("UTC-3"))
                .andExpect(jsonPath("$.currency").value(69d))
                .andExpect(jsonPath("$.distance").value(893));
    }

    @Test
    public void getStaticsReturnsOk() throws Exception {
        when(ipTracerService.getStatistics()).thenReturn(EntityManagerFactory.getStatisticsResponse());
        mvc.perform(MockMvcRequestBuilders.get("/v1/api/statistics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statistics.[0].countryName").value("Brasil"))
                .andExpect(jsonPath("$.statistics.[0].distance").value(2862))
                .andExpect(jsonPath("$.statistics.[0].invocations").value(10))
                .andExpect(jsonPath("$.statistics.[1].countryName").value("España"))
                .andExpect(jsonPath("$.statistics.[1].distance").value(10040))
                .andExpect(jsonPath("$.statistics.[1].invocations").value(5))
                .andExpect(jsonPath("$.average").value(5254));
    }

    @Test
    public void throwsBadRequestWithBadIpAddressInformation() throws Exception {
        String ip = "10.a.10";
        when(ipTracerService.logRequest(any())).thenReturn(EntityManagerFactory.getInfoRequest());
        when(ipTracerService.buildResponse(any())).thenThrow(new RestClientException("", HttpStatus.BAD_REQUEST));
        mvc.perform(MockMvcRequestBuilders.get("/v1/api/information?ip="+ip).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
