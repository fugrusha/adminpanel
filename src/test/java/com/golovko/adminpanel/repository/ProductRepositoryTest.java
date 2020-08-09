package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductRepositoryTest extends BaseTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByCategoryIdAndOrderNumber() {
        Category c1 = testObjectFactory.createCategory();
        Category c2 = testObjectFactory.createCategory();

        Product p1 = testObjectFactory.createProduct(c1, 2);
        testObjectFactory.createProduct(c2, 2);
        testObjectFactory.createProduct(c1, 1);

        Product product = productRepository.findByCategoryIdAndOrderNumber(c1.getId(), p1.getOrderNumber());

        Assert.assertEquals(p1.getId(), product.getId());
    }

    @Test
    public void testFindByIdAndCategoryId() {
        Category c1 = testObjectFactory.createCategory();
        Category c2 = testObjectFactory.createCategory();

        Product p1 = testObjectFactory.createProduct(c1, 2);
        testObjectFactory.createProduct(c2, 2);
        testObjectFactory.createProduct(c1, 1);

        Product product = productRepository.findByIdAndCategoryId(p1.getId(), c1.getId());

        Assert.assertEquals(p1.getId(), product.getId());
    }

    @Test
    public void testFindByCategoryId() {
        Category c1 = testObjectFactory.createCategory();
        Category c2 = testObjectFactory.createCategory();

        Product p1 = testObjectFactory.createProduct(c1, 2);
        Product p2 = testObjectFactory.createProduct(c1, 1);
        testObjectFactory.createProduct(c2, 1);

        List<Product> products = productRepository.findByCategoryId(c1.getId());

        Assertions.assertThat(products).extracting("id")
                .containsExactlyInAnyOrder(p1.getId(), p2.getId());
    }
}
