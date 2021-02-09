package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ProductDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 3, max = 60, message = "Name size must be between 3 and 60 characters")
    @NotBlank(message = "Name is mandatory!")
    private String name;

    @NotBlank(message = "Description is mandatory!")
    private String description;

    @Positive(message = "Price must be a positive value")
    private Double price;

    private String imgUrl;

    @PastOrPresent(message = "Product's date cannot lie in the future. Please revise.")
    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }

    public ProductDTO(Product entity, Set<Category> categories) {
            this(entity);
            categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }

}
