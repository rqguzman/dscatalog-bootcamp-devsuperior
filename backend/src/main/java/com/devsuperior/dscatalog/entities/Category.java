package com.devsuperior.dscatalog.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
