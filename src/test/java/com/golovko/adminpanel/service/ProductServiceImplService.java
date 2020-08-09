package com.golovko.adminpanel.service;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.domain.Product;
import com.golovko.adminpanel.dto.product.ProductCreateDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;
import com.golovko.adminpanel.exception.EntityAlreadyExistsException;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ProductServiceImplService extends BaseTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct() {
        Category category = testObjectFactory.createCategory();

        ProductCreateDTO createDTO = new ProductCreateDTO();
        createDTO.setName("name");
        createDTO.setDescription("description");
        createDTO.setPhotoUrl("url");
        createDTO.setOrderNumber(2);
        createDTO.setPrice(55.1);

        ProductReadDTO readDTO = productService.createProduct(category.getId(), createDTO);

        Assertions.assertThat(createDTO).isEqualToComparingFieldByField(readDTO);

        Product product = productRepository.findById(readDTO.getId()).get();
        Assertions.assertThat(createDTO).isEqualToComparingFieldByField(product);
        Assert.assertEquals(category.getId(), product.getCategory().getId());
    }

    @Test
    public void testCreateProductWithSameOrderNumberInCategory() {
        Category category = testObjectFactory.createCategory();

        ProductCreateDTO createDTO = new ProductCreateDTO();
        createDTO.setName("name");
        createDTO.setDescription("description");
        createDTO.setPhotoUrl("url");
        createDTO.setOrderNumber(2);
        createDTO.setPrice(55.1);

        productService.createProduct(category.getId(), createDTO);

        Assertions.assertThatThrownBy(() -> productService.createProduct(category.getId(), createDTO))
                .isInstanceOf(EntityAlreadyExistsException.class);
    }

    @Test
    public void testGetProductByOrderNum() {
        Category category = testObjectFactory.createCategory();

        Product p1 = testObjectFactory.createProduct(category, 1);
        testObjectFactory.createProduct(category, 2);
        testObjectFactory.createProduct(category, 3);

        ProductReadDTO readDTO = productService.getProductByOrderNum(category.getId(), p1.getOrderNumber());

        Assertions.assertThat(p1).isEqualToIgnoringGivenFields(readDTO, "category");
        Assert.assertEquals(category.getId(), readDTO.getCategoryId());
    }

    @Test
    public void testGetAllProductsByCategoryId() {
        Category c1 = testObjectFactory.createCategory();
        Category c2 = testObjectFactory.createCategory();

        Product p1 = testObjectFactory.createProduct(c1, 1);
        Product p2 = testObjectFactory.createProduct(c1, 2);
        testObjectFactory.createProduct(c2, 3);

        List<ProductReadDTO> allProducts = productService.getAllProducts(c1.getId());

        Assertions.assertThat(allProducts).extracting("id")
                .containsExactlyInAnyOrder(p1.getId(), p2.getId());
    }

    @Test
    public void testPatchProduct() {
        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);

        ProductPatchDTO patchDTO = new ProductPatchDTO();
        patchDTO.setDescription("new description");
        patchDTO.setName("new name");
        patchDTO.setPhotoUrl("new url");
        patchDTO.setOrderNumber(10);
        patchDTO.setPrice(1000.0);

        ProductReadDTO readDTO = productService.patchProduct(category.getId(), p1.getId(), patchDTO);

        Assertions.assertThat(patchDTO).isEqualToComparingFieldByField(patchDTO);
        Assert.assertEquals(category.getId(), readDTO.getCategoryId());

        Product product = productRepository.findById(p1.getId()).get();
        Assertions.assertThat(patchDTO).isEqualToIgnoringGivenFields(product, "categoryId");
        Assert.assertEquals(category.getId(), product.getCategory().getId());
    }

    @Test
    public void testPatchProductEmptyPatch() {
        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);

        ProductPatchDTO patchDTO = new ProductPatchDTO();

        ProductReadDTO readDTO = productService.patchProduct(category.getId(), p1.getId(), patchDTO);
        Assertions.assertThat(readDTO).hasNoNullFieldsOrProperties();

        Product product = productRepository.findById(p1.getId()).get();
        Assertions.assertThat(p1).isEqualToIgnoringGivenFields(product, "category");
        Assert.assertEquals(category.getId(), product.getCategory().getId());
    }

    @Test
    public void testDeleteProduct() {
        Category c1 = testObjectFactory.createCategory();

        Product p1 = testObjectFactory.createProduct(c1, 1);
        testObjectFactory.createProduct(c1, 2);

        productService.deleteProduct(c1.getId(), p1.getId());

        Assert.assertFalse(productRepository.existsById(p1.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNotExistProduct() {
        Category c1 = testObjectFactory.createCategory();

        productService.deleteProduct(c1.getId(), UUID.randomUUID());
    }
}
