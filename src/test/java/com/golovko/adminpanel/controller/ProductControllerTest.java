package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.dto.product.ProductCreateDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;
import com.golovko.adminpanel.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        ProductReadDTO readDTO = createProductReadDTO();

        List<ProductReadDTO> expectedResult = List.of(readDTO);

        Mockito.when(productService.getAllProducts(readDTO.getCategoryId())).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/categories/{categoryId}/products/", readDTO.getCategoryId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<ProductReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(productService).getAllProducts(readDTO.getCategoryId());
    }

    @Test
    public void testGetProductByOrderNum() throws Exception {
        ProductReadDTO readDTO = createProductReadDTO();

        Mockito.when(productService.getProductByOrderNum(readDTO.getCategoryId(), readDTO.getOrderNumber()))
                .thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(get("/api/v1/categories/{categoryId}/products/{order}",
                        readDTO.getCategoryId(), readDTO.getOrderNumber()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductReadDTO actualResult = objectMapper.readValue(resultJson, ProductReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(productService).getProductByOrderNum(readDTO.getCategoryId(), readDTO.getOrderNumber());
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductReadDTO readDTO = createProductReadDTO();

        ProductCreateDTO createDTO = new ProductCreateDTO();
        createDTO.setName("name");
        createDTO.setDescription("description");
        createDTO.setPhotoUrl("url");
        createDTO.setOrderNumber(2);
        createDTO.setPrice(55.1);

        Mockito.when(productService.createProduct(readDTO.getCategoryId(), createDTO)).thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(post("/api/v1/categories/{categoryId}/products/", readDTO.getCategoryId())
                .content(objectMapper.writeValueAsString(createDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductReadDTO actualResult = objectMapper.readValue(resultJson, ProductReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(productService).createProduct(readDTO.getCategoryId(), createDTO);
    }

    @Test
    public void testPatchProduct() throws Exception {
        ProductReadDTO readDTO = createProductReadDTO();

        ProductPatchDTO patchDTO = new ProductPatchDTO();
        patchDTO.setDescription("new description");
        patchDTO.setName("new name");
        patchDTO.setPhotoUrl("new url");
        patchDTO.setOrderNumber(10);
        patchDTO.setPrice(1000.0);

        Mockito.when(productService.patchProduct(readDTO.getCategoryId(), readDTO.getId(), patchDTO))
                .thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(patch("/api/v1/categories/{categoryId}/products/{id}",
                        readDTO.getCategoryId(), readDTO.getId())
                .content(objectMapper.writeValueAsString(patchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductReadDTO actualResult = objectMapper.readValue(resultJson, ProductReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(productService).patchProduct(readDTO.getCategoryId(), readDTO.getId(), patchDTO);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        UUID categoryId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/categories/{categoryId}/products/{id}", categoryId, id))
                .andExpect(status().isOk());

        Mockito.verify(productService).deleteProduct(categoryId, id);
    }

    private ProductReadDTO createProductReadDTO() {
        ProductReadDTO dto = new ProductReadDTO();
        dto.setId(UUID.randomUUID());
        dto.setName("product name");
        dto.setDescription("description");
        dto.setPrice(55.10);
        dto.setPhotoUrl("url");
        dto.setOrderNumber(2);
        dto.setCategoryId(UUID.randomUUID());
        return dto;
    }

}
