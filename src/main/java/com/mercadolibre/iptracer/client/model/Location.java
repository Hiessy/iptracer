package com.mercadolibre.iptracer.client.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Location {
    private String capital;
    @JsonRawValue
    ArrayList < Language > languages = new ArrayList < Language > ();
    private boolean isEu;

    public Location(String capital, ArrayList<Language> languages, boolean isEu) {
        this.capital = capital;
        this.languages = languages;
        this.isEu = isEu;
    }

    public Location() {
    }
}

