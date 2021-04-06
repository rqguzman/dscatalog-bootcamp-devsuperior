package com.devsuperior.dscatalog.tests.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.MyDatabaseIntegrityException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.factory.ProductFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    // Allows Java objects conversion to JSON
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    private long existingId;
    private long nonExistentId;
    private long dependentId;
    private ProductDTO newProductDTO;
    private ProductDTO existingProductDTO;
    private PageImpl<ProductDTO> page;
    private String operatorUsername;
    private String operatorPassword;

    @BeforeEach
    void setUp() throws Exception {

        operatorUsername = "alex@gmail.com";
        operatorPassword = "123456";
        existingId = 1L;
        nonExistentId = 2L;
        dependentId = 3L;

        newProductDTO = ProductFactory.createProductDTO(null);
        existingProductDTO = ProductFactory.createProductDTO(existingId);

        page = new PageImpl<>(List.of(existingProductDTO));

        // CREATE
            // mock service to insert a new ProductDTO
            // insert an object was too complex. So any() was used instead.
            when(service.insert(any())).thenReturn(existingProductDTO);

        // RETRIEVE
            // mock service to return a productDTO
            when(service.findById(existingId)).thenReturn(existingProductDTO);
            when(service.findById(nonExistentId)).thenThrow(ResourceNotFoundException.class);

            // mock service to return a page of ProductsDTO
            when(service.findAllPaged(any(), anyString(), any())).thenReturn(page);

        //UPDATE
            // mock service to UPDATE a ProductDTO
            // eq() method replaces the actual existingId. It won't work on the other hand.
            when(service.update(eq(existingId), any())).thenReturn(existingProductDTO);

            // mock service when trying to update nonexistent ProductDTO
            when(service.update(eq(nonExistentId), any())).thenThrow(ResourceNotFoundException.class);

        // DELETE
            // mock service to DELETE an existing ProductDTO
            // .delete() returns void, hence we start with doNothing()
            doNothing().when(service).delete(existingId);

            // mock service to DELETE an nonexistent ProductDTO
            doThrow(ResourceNotFoundException.class).when(service).delete(nonExistentId);

            // mock service to DELETE a ProductDTO associated with another dependant object
            /**
             * NOTE: the way system entities associations are configured, there won't be
             * unwanted effects due deletion of dependent objects, but since these are unit
             * tests, we are covering this possibility.
             **/
            doThrow(MyDatabaseIntegrityException.class).when(service).delete(dependentId);
    }

    // CREATE
    @Test
    public void insertShouldReturnHttpStatusCreatedAndProductWhenDataIsValid() throws Exception{
        // given
        // To insert a product, one must be logged
        String accessToken = obtainAccessToken(operatorUsername, operatorPassword);
        // includes a valid Product into request body
        String jsonBody = objectMapper.writeValueAsString(newProductDTO);

        // when
        // mock a request
        ResultActions result = mockMvc.perform(post("/products")// url
                .header("Authorization", "Bearer " + accessToken)// token
                .content(jsonBody)// body
                .contentType(MediaType.APPLICATION_JSON)// body type
                .accept(MediaType.APPLICATION_JSON));// returned body type accepted


        // then
        // asserts that we expect a http response status 201 - created
        result.andExpect(status().isCreated());
        // check on returned object
        result.andExpect(jsonPath("$.id").exists());// ID shall not be null
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenNegativePrice() throws Exception{
        // given
        // To insert a product, one must be logged
        String accessToken = obtainAccessToken(operatorUsername, operatorPassword);
        // provide a negative price
        newProductDTO.setPrice(-10.00);
        // includes a valid Product into request body
        String jsonBody = objectMapper.writeValueAsString(newProductDTO);

        // when
        // mock a request
        ResultActions result = mockMvc.perform(post("/products")// url
                .header("Authorization", "Bearer " + accessToken)// token
                .content(jsonBody)// body
                .contentType(MediaType.APPLICATION_JSON)// body type
                .accept(MediaType.APPLICATION_JSON));// returned body type accepted


        // then
        // asserts that we expect a http response status 201 - created
        result.andExpect(status().isUnprocessableEntity());
    }

    // RETRIEVE
    @Test
    public void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        // Test JSON values within the response's body
        result.andExpect(jsonPath("$.content").exists());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        // Test JSON values within the response's body
        result.andExpect(jsonPath("$.id").exists());// if returned id exists
        result.andExpect(jsonPath("$.id").value(existingId));// if returned id matches w/ passed id
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", nonExistentId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    // UPDATE
    // Method shall return 404, when ID is nonexistent
    @Test
    public void updateShouldReturnNotFindWhenIdDoesNotExist () throws Exception{
        // /products/{id} is a protected route. We need an access token.
        String accessToken = obtainAccessToken(operatorUsername, operatorPassword);

        // Put requests demands for a request body. Http protocol use JSON to send data.
        // This line converts java object to json
        String jsonBody = objectMapper.writeValueAsString(newProductDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistentId)
                .header("Authorization", "Bearer " + accessToken)// Adds access token to header
                .content(jsonBody)// Adds json object to body request
                .contentType(MediaType.APPLICATION_JSON)// Configure MediaType
                .accept(MediaType.APPLICATION_JSON));

        // asserts that we expect a http response status 404 - Not Found
        result.andExpect(status().isNotFound());
    }

    // Method shall return a ProductDTO object when id exists
    @Test
    public void updateShouldReturnProductDTOWhenIdExists () throws Exception{

        String accessToken = obtainAccessToken(operatorUsername, operatorPassword);

        String jsonBody = objectMapper.writeValueAsString(newProductDTO);

        // helps checking if returned ProductDTO is correct
        String expectedName = newProductDTO.getName();
        Double expectedPrice = newProductDTO.getPrice();

        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // asserts that we expect a http response status 200 - Ok
        result.andExpect(status().isOk());

        // check on returned object
        result.andExpect(jsonPath("$.id").exists());// ID shall not be null
        result.andExpect(jsonPath("$.id").value(existingId));// Object id matches the informed one
        result.andExpect(jsonPath("$.name").value(expectedName));// Object name matches the informed one
        result.andExpect(jsonPath("$.price").value(expectedPrice));// Object Price equals the informed one

    }

    // DELETE
    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception{
        // given
        // /products/{id} is a protected route. We need an access token.
        String accessToken = obtainAccessToken(operatorUsername, operatorPassword);

        // when
        // mock a delete request
        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId)
                .header("Authorization", "Bearer " + accessToken)// Adds access token to header
                .accept(MediaType.APPLICATION_JSON));

        // asserts that we expect a http response status 204 - No Content
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        // given
        // /products/{id} is a protected route. We need an access token.
        String accessToken = obtainAccessToken(operatorUsername, operatorPassword);

        // when
        // mock a delete request
        ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistentId)
                .header("Authorization", "Bearer " + accessToken)// Adds access token to header
                .accept(MediaType.APPLICATION_JSON));

        // asserts that we expect a http response status 404 - No Content
        result.andExpect(status().isNotFound());
    }

    // AUTENTICATION
    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        // A different way to access the response message attributes
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}