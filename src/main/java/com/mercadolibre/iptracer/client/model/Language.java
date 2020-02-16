package com.mercadolibre.iptracer.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Language {
    private String code;
    private String name;
    @JsonProperty("native")
    private String nativeLanguage;

    public Language(String code, String name, String nativeLanguage) {
        this.code = code;
        this.name = name;
        this.nativeLanguage = nativeLanguage;
    }

    public Language() {
    }
}

