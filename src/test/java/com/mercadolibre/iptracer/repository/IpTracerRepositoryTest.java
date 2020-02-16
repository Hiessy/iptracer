package com.mercadolibre.iptracer.repository;


import com.mercadolibre.iptracer.model.RequestInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class IpTracerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IpTracerRepository repository;
    private String ipArg = "13.13.13.13";
    private String countryArg = "Argentina";
    private Integer distanceArg = 0;
    private String id = ipArg+";"+countryArg+";"+distanceArg;

    private String ipChina = "123.123.123.123";
    private String countryChina = "China";
    private Integer distanceChina = 11356;

    private String ipFrance = "23.23.23.23";
    private String countryFrance = "France";
    private Integer distanceFrance = 2356;

    private String ipItaly = "24.24.24.24";
    private String countryItaly = "Italy";
    private Integer distanceItaly = 1856;
    @Test
    public void saveANewInfoRequest() {
        repository.save(new RequestInfo(ipArg,countryArg,distanceArg));
        Optional<RequestInfo> requestInfo2 = repository.findById(id);
        assertThat(requestInfo2.get()).extracting(RequestInfo::getCountry).isEqualTo(countryArg);
    }

    @Test
    public void findAllInfoRequest() {
        repository.save(new RequestInfo(ipArg,countryArg,distanceArg));
        repository.save(new RequestInfo(ipChina,countryChina,distanceChina));
        List<String> result = new ArrayList<String>(){{add(countryArg);add(countryChina);}};
        Iterable<RequestInfo> requestInfo = repository.findAll();
        assertThat(requestInfo).extracting(RequestInfo::getCountry).isEqualTo(result);
    }

    @Test
    public void findAllInfoRequestWhenMoraThanOne() {
        repository.save(new RequestInfo(ipArg, countryArg, distanceArg));
        repository.save(new RequestInfo(ipFrance, countryFrance, distanceFrance));
        repository.save(new RequestInfo(ipChina, countryChina, distanceChina));
        List<String> result = new ArrayList<String>(){{add(countryArg);add(countryFrance);add(countryChina);}};
        Iterable<RequestInfo> requestInfo = repository.findAll();
        assertThat(requestInfo).extracting(RequestInfo::getCountry).isEqualTo(result);
    }

    @Test
    public void updatesOneSameIpAddressCountryAndDistance() {
        repository.save(new RequestInfo(ipArg, countryArg, distanceArg));
        repository.save(new RequestInfo(ipFrance, countryFrance, distanceFrance));
        repository.save(new RequestInfo(ipChina, countryChina, distanceChina));
        repository.save(new RequestInfo(ipItaly, countryItaly,distanceItaly));
        repository.save(new RequestInfo(ipArg, countryChina,distanceItaly));
        List<String> result = new ArrayList<String>(){{add(countryArg);add(countryFrance);add(countryChina);add(countryItaly);add(countryChina);}};
        Iterable<RequestInfo> requestInfo = repository.findAll();
        assertThat(requestInfo).extracting(RequestInfo::getCountry).isEqualTo(result);
    }

}
