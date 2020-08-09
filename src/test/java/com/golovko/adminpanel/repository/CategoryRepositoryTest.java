package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryRepositoryTest extends BaseTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetAllCategories() {
        Category c1 = testObjectFactory.createCategory();
        Category c2 = testObjectFactory.createCategory();

        List<Category> allCategories = categoryRepository.getAllCategories();

        Assertions.assertThat(allCategories).extracting("id")
                .containsExactlyInAnyOrder(c1.getId(), c2.getId());
    }

    @Test
    public void testExistsByName() {
        Category c1 = testObjectFactory.createCategory();

        Assert.assertTrue(categoryRepository.existsByName(c1.getName()));
        Assert.assertFalse(categoryRepository.existsByName("random name"));
    }
}
