package com.devsuperior.dscatalog.resources.exceptions;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError extends StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)// omits setter.
    private List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        errors.add( new FieldMessage(fieldName, message));
    }


}
