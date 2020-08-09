package com.golovko.adminpanel.service;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.dto.category.CategoryCreateDTO;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.category.CategoryReadDTO;
import com.golovko.adminpanel.exception.EntityAlreadyExistsException;
import com.golovko.adminpanel.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImplTest extends BaseTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetAllCategories() {
        Category c1 = testObjectFactory.createCategory();
        Category c2 = testObjectFactory.createCategory();
        Category c3 = testObjectFactory.createCategory();

        List<CategoryReadDTO> categories = categoryService.getAllCategories();

        Assertions.assertThat(categories).extracting("id")
                .containsExactlyInAnyOrder(c1.getId(), c2.getId(), c3.getId());
    }

    @Test
    public void testCreateCategory() {
        CategoryCreateDTO createDTO = new CategoryCreateDTO();
        createDTO.setName("condoms");

        List<CategoryReadDTO> categories = categoryService.createCategory(createDTO);

        CategoryReadDTO readDTO = categories.get(0);
        Assert.assertEquals(createDTO.getName(), readDTO.getName());

        Category category = categoryRepository.findById(readDTO.getId()).get();
        Assert.assertEquals(createDTO.getName(), category.getName());
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testCreateCategoryThatAlreadyExists() {
        Category c1 = testObjectFactory.createCategory();
        c1.setName("baloons");
        categoryRepository.save(c1);

        CategoryCreateDTO createDTO = new CategoryCreateDTO();
        createDTO.setName("baloons");

        categoryService.createCategory(createDTO);
    }

    @Test
    public void testPatchCategory() {
        Category c1 = testObjectFactory.createCategory();

        CategoryPatchDTO patchDTO = new CategoryPatchDTO();
        patchDTO.setName("new category name");

        List<CategoryReadDTO> categories = categoryService.patchCategory(c1.getId(), patchDTO);

        CategoryReadDTO readDTO = categories.get(0);
        Assert.assertEquals(patchDTO.getName(), readDTO.getName());

        Category category = categoryRepository.findById(c1.getId()).get();
        Assert.assertEquals(patchDTO.getName(), category.getName());
    }

    @Test
    public void testDeleteCategory() {
        Category c1 = testObjectFactory.createCategory();

        categoryService.deleteCategory(c1.getId());

        Assert.assertFalse(categoryRepository.existsById(c1.getId()));
    }
}
