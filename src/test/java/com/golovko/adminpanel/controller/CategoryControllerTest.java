package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.dto.category.CategoryCreateDTO;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.category.CategoryReadDTO;
import com.golovko.adminpanel.service.CategoryService;
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
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetAllCategories() throws Exception {
        CategoryReadDTO c1 = createCategoryReadDTO();
        CategoryReadDTO c2 = createCategoryReadDTO();
        List<CategoryReadDTO> expectedResult = List.of(c1, c2);

        Mockito.when(categoryService.getAllCategories()).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CategoryReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(categoryService).getAllCategories();
    }

    @Test
    public void testCreateCategory() throws Exception {
        CategoryReadDTO c1 = createCategoryReadDTO();
        CategoryReadDTO c2 = createCategoryReadDTO();
        List<CategoryReadDTO> expectedResult = List.of(c1, c2);

        CategoryCreateDTO createDTO = new CategoryCreateDTO();
        createDTO.setName("category name");

        Mockito.when(categoryService.createCategory(createDTO)).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CategoryReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(categoryService).createCategory(createDTO);
    }

    @Test
    public void testPatchCategory() throws Exception {
        CategoryReadDTO c1 = createCategoryReadDTO();
        CategoryReadDTO c2 = createCategoryReadDTO();
        List<CategoryReadDTO> expectedResult = List.of(c1, c2);

        CategoryPatchDTO patchDTO = new CategoryPatchDTO();
        patchDTO.setName("category name");

        Mockito.when(categoryService.patchCategory(c1.getId(), patchDTO)).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(patch("/api/v1/categories/{id}", c1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CategoryReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(categoryService).patchCategory(c1.getId(), patchDTO);
    }

    @Test
    public void testDeleteCategory() throws Exception {
        UUID categoryId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk());

        Mockito.verify(categoryService).deleteCategory(categoryId);
    }

    private CategoryReadDTO createCategoryReadDTO() {
        CategoryReadDTO dto = new CategoryReadDTO();
        dto.setId(UUID.randomUUID());
        dto.setName("name");
        return dto;
    }
}
