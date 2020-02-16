package com.mercadolibre.iptracer.service;

import com.mercadolibre.iptracer.exceptions.RestClientException;
import com.mercadolibre.iptracer.exceptions.ServiceException;
import com.mercadolibre.iptracer.model.IpTracerResponse;
import com.mercadolibre.iptracer.model.RequestInfo;
import com.mercadolibre.iptracer.model.StatisticsResponse;


import java.util.List;

public interface IpTracerService {
    RequestInfo logRequest(String ip) throws RestClientException, ServiceException;
    IpTracerResponse buildResponse(String ip) throws RestClientException, ServiceException;
    List<StatisticsResponse> getStatistics() throws RestClientException, ServiceException;
}
