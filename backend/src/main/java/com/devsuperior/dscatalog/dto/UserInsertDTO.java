package com.devsuperior.dscatalog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInsertDTO extends UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String password;

    public UserInsertDTO() {
        super();
    }

}
