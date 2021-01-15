package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO catDTO = categoryService.findById(id);
        return ResponseEntity.ok().body(catDTO);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO catDTO) {

        catDTO = categoryService.insert(catDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                        .path("/{id}")
                                        .buildAndExpand(catDTO.getId())
                                        .toUri();

        return ResponseEntity.created(uri).body(catDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO catDTO) {

        catDTO = categoryService.update(id, catDTO);

        return ResponseEntity.ok().body(catDTO);
    }

}
