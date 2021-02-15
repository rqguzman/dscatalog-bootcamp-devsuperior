package com.devsuperior.dscatalog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UriDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uri;

    public UriDTO() {
    }

    public UriDTO(String uri) {
        this.uri = uri;
    }
}
