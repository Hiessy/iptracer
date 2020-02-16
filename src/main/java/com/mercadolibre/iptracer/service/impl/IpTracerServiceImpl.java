package com.mercadolibre.iptracer.service.impl;

import com.mercadolibre.iptracer.client.CountryCodeInfoClient;
import com.mercadolibre.iptracer.client.CountryIpInfoClient;
import com.mercadolibre.iptracer.client.CurrencyInfoClient;
import com.mercadolibre.iptracer.client.model.CountryCodeInfoResponse;
import com.mercadolibre.iptracer.client.model.CountryIpInfoResponse;
import com.mercadolibre.iptracer.client.model.Language;
import com.mercadolibre.iptracer.exceptions.RestClientException;
import com.mercadolibre.iptracer.model.IpTracerResponse;
import com.mercadolibre.iptracer.model.RequestInfo;
import com.mercadolibre.iptracer.model.StatisticsResponse;
import com.mercadolibre.iptracer.repository.IpTracerRepository;
import com.mercadolibre.iptracer.service.IpTracerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;


@Slf4j
@Service
public class IpTracerServiceImpl implements IpTracerService {

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    @Autowired
    private CountryIpInfoClient countryIpInfoClient;
    @Autowired
    private CountryCodeInfoClient countryCodeInfoClient;
    @Autowired
    private CurrencyInfoClient currencyInfoClient;
    @Autowired
    private IpTracerRepository repository;

    /**
     * Will log the incoming request user information.
     *
     * @param ip the ip address from the user requesting to use the service.
     * @return RequestInfo object if persisted correctly
     * @throws RestClientException If we can't validate the incoming request ip
     */
    public RequestInfo logRequest(String ip) throws RestClientException {
        log.info("Retrieving address information for request ip: " + ip);
        CountryIpInfoResponse countryIpInfoRequestIp = countryIpInfoClient.getCountryInfoByIp(ip);
        if(countryIpInfoRequestIp.getLatitude() != null) {
            Integer distance = calculateDistance(countryIpInfoRequestIp.getLatitude(), countryIpInfoRequestIp.getLongitude());
            String country = countryIpInfoRequestIp.getCountryName();
            Optional<RequestInfo> requestInfo = repository.findById(ip + ":" + country + ":" + distance);
            requestInfo.ifPresent(RequestInfo::addCounter);
            return repository.save(requestInfo.orElse(new RequestInfo(ip, country, distance)));
        } else {
            log.error("Ip address not found for ip: " + ip);
            throw new RestClientException("We are unable to determine the request Ip address, unable to use service", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Will return all data base entries and filter out on the max and min distance countries.
     *
     * @return List<StatisticsResponse> containing the min and max values
     */
    public List<StatisticsResponse> getStatistics() {
        Iterable<RequestInfo> requestInfo = repository.findAll();
        StatisticsResponse maxDistance = StreamSupport.stream(requestInfo.spliterator(), false).map(c -> new StatisticsResponse(c.getCountry(),c.getDistance(),c.getCount())).max(Comparator.comparingInt(StatisticsResponse::getDistance)).orElse(new StatisticsResponse("",0,0));
        StatisticsResponse minDistance = StreamSupport.stream(requestInfo.spliterator(), false).map(c -> new StatisticsResponse(c.getCountry(),c.getDistance(),c.getCount())).min(Comparator.comparingInt(StatisticsResponse::getDistance)).orElse(new StatisticsResponse("",0,0));
        List<StatisticsResponse> response = new ArrayList<StatisticsResponse>(){{
            add(maxDistance);
            add(minDistance);
        }};
        return response;
    }

    /**
     * Build the response from the query.
     *
     * @param ip the queries ip address.
     * @return IpTracerResponse fir the queried ip.
     * @throws RestClientException if the queried ip address was not found
     */
   public IpTracerResponse buildResponse(String ip) throws RestClientException {
       log.info("Building response for ip: " + ip);
       CountryIpInfoResponse countryInfoIpQuery = countryIpInfoClient.getCountryInfoByIp(ip);
       if (countryInfoIpQuery.getLatitude() != null && countryInfoIpQuery.getLongitude() != null  && countryInfoIpQuery.getLatitude() != null) {
           Integer distance = calculateDistance(countryInfoIpQuery.getLatitude(), countryInfoIpQuery.getLongitude());
           String code = countryInfoIpQuery.getCountryCode();
           String name = countryInfoIpQuery.getCountryName();
           List<Language> languages = countryInfoIpQuery.getLocation().getLanguages();
           log.info("Searching country info for code: " + code);
           CountryCodeInfoResponse countryCodeInfoResponse = countryCodeInfoClient.getCountryInfoByCode(code);

           String countryCurrencyCode = countryCodeInfoResponse.getCurrencies().get(0).getCode();
           List<String> timezone = countryCodeInfoResponse.getTimezones();
           Double currencies = (countryCurrencyCode.equals("USD")) ? 1 : getUSDRate(countryCurrencyCode);
           log.info("Searching for USD rates: " + code);
           return new IpTracerResponse(code, name, languages, currencies, timezone, distance);
       } else {
           log.info("Queried ip address is incorrect: " + ip);
           throw new RestClientException("ip address not found maybe be invalid.", HttpStatus.NOT_FOUND);
       }
   }

    private Integer calculateDistance(Double userLat, Double userLng) {
        Double venueLat = -34.611778259277344;
        Double venueLng = -58.41730880737305;
        Double latDistance = Math.toRadians(userLat - venueLat);
        Double lngDistance = Math.toRadians(userLng - venueLng);

        Double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) Math.round(AVERAGE_RADIUS_OF_EARTH * c);
    }

    private Double getUSDRate(String countryCodeInfoResponse) {
        try {
            HashMap<String, Double> res = currencyInfoClient.getCurrencyInfo(countryCodeInfoResponse).getRates();
            if (res.containsKey(countryCodeInfoResponse))
                return res.get("USD") * res.get(countryCodeInfoResponse);
            else
                return 0.0;
        } catch (Exception e) {
            log.error("Unable to determine rates exception is: " + e.getMessage(), e);
            return 0.0;
        }

    }
}
