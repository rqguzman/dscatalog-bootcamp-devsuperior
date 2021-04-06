package com.devsuperior.dscatalog.tests.repositories;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.tests.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Carrega apenas as necessárias as consultas JPA
 * após cada chamada a cada um dos testes, executa
 * um rollback no DB, para que o teste seguinte
 * encontre o DB em seu estado inicial (após o seed)
 * */
@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;
    private long countPCGamerProducts;
    private long countCategory3Products;
    private Pageable pageable;
    private List<Category> categories = new ArrayList<>();
    private long countTotalProductsByCategory;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
        countPCGamerProducts = 21L;
        countCategory3Products = 23L;
        pageable = PageRequest.of(0,10);
        countTotalProductsByCategory = 23L;
    }

    // FIND - CATEGORY RELATED TESTS
    @Test
    public void findShouldNotReturnAnyProductsWhenCategoryDoesNotRelateToAnyProduct (){

        categories.add(new Category(4L, "Garden"));

        Page<Product> result = repository.find(categories, "", pageable);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findShouldReturnAllRelatedProductsWhenCategoryExists (){

        categories.add(new Category(3L, "computers"));

        Page<Product> result = repository.find(categories, "", pageable);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalProductsByCategory, result.getTotalElements());
    }

    @Test
    public void findShouldReturnAllProductsWhenCategoryNameIsNotInformed() {
        // Given
        List<Category> categories = null;

        // When
        Page<Product> result = repository.find(categories, "", pageable);

        // Then
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findShouldReturnOnlyCategoryRelatedProducts() {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(3L, "Computers"));

        // When
        Page<Product> result = repository.find(categories, "", pageable);

        // Then
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countCategory3Products, result.getTotalElements());
    }

    // FIND - PRODUCT NAME RELATED TESTS
    @Test
    public void findShouldReturnAllProductsWhenNameIsEmpty() {
        String name = "";

        Page<Product> result = repository.find(null, name, pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findShouldReturnProductsWhenNameExistsIgnoringCase() {
        String name = "pc gAMeR";

        Page<Product> result = repository.find(null, name, pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
    }

    @Test
    public void findShouldReturnProductsWhenNameExists() {
        String name = "PC Gamer";

        Page<Product> result = repository.find(null, name, pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
    }

    // DELETE
    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);

        /**
         * '.findById()' retorna um Optional<T>
         *  A assertion, então, está sendo feita em cima
         *  da variável, utilizando o método '.assertFalse()'
         *  que recebe como parâmetro uma chamada ao método
         *  '.isPresent()' da classe Optional<T>
         *  Se o método '.deleteById()' funcionar, não haverá
         *  um objeto Product atribuído à variável result,
         *  o assert receberá 'false' e o teste passará.
         * */
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){

        Product product = ProductFactory.createProduct();
        product.setId(null);

        product = repository.save(product);
        Optional<Product> result = repository.findById(product.getId());

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), product);
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

}
