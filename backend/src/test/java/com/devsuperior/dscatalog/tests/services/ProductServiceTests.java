package com.devsuperior.dscatalog.tests.services;


import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.MyDatabaseIntegrityException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Product product;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        product = ProductFactory.createProduct();
        page = new PageImpl<>(List.of(product));

        // Service behaviour simulations
        Mockito.when(repository.find(ArgumentMatchers.any(), ArgumentMatchers.anyString(), ArgumentMatchers.any()))
            .thenReturn(page);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.getOne(existingId)).thenReturn(product);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        // given
        // check @BeforeEach for nonExistingId = 1000L
        ProductDTO dto = new ProductDTO();

        // when
        Executable executable = () -> service.update(nonExistingId, dto);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        // given
        // check @BeforeEach for existingId = 1L
        ProductDTO dto = new ProductDTO();

        // when
        ProductDTO result = service.update(existingId, dto);

        // then
        // If id exists, result shouldn't be null
        Assertions.assertNotNull(result);
    }

    @Test
    public void findBYIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        // given
        // check @BeforeEach for nonExistingId = 1000L

        // when
        Executable executable = () -> service.findById(nonExistingId);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        // given
        // check @BeforeEach for existingId = 1L

        // when
        ProductDTO result = service.findById(existingId);

        // then
        Assertions.assertNotNull(result);
    }

    @Test
    public void findAllPagedShouldReturnPage(){
        // given
        Long categoryId = 0L;
        String name = "";
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<ProductDTO> result = service.findAllPaged(categoryId, name, pageRequest);

        // then
        // A page MUST be returned
        Assertions.assertNotNull(result);
        // Can't be empty, because findAllPaged invokes a repository's .find() method
        // mocked with a list of existing objects.
        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(repository, Mockito.times(1)).find(null, name, pageRequest);
    }

    @Test
    public void deleteShouldThrowMyDatabaseIntegrityExceptionWhenDependentId(){
        Assertions.assertThrows(MyDatabaseIntegrityException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }
}
