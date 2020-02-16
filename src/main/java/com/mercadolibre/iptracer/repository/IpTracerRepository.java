package com.mercadolibre.iptracer.repository;

import com.mercadolibre.iptracer.model.RequestInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpTracerRepository extends CrudRepository<RequestInfo, String> {
}