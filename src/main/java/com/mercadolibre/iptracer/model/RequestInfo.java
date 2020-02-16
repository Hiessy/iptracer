package com.mercadolibre.iptracer.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RequestInfo {

    @Id
    private String id;
    private String ip;
    private Integer count;
    private String country;
    private Integer distance;

    public RequestInfo() {
    }

    public RequestInfo(String ip, String country, Integer distance) {
        this.id = ip+":"+country+":"+distance;
        this.ip = ip;
        this.country = country;
        this.distance = distance;
        this.count = 1;
    }

    public void addCounter(){
        this.count++;
    }

    public RequestInfo(String ip, Integer count, String country, Integer distance) {
        this.id = ip+":"+country+":"+distance;
        this.ip = ip;
        this.count = count;
        this.country = country;
        this.distance = distance;
    }
}
