package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.domain.Product;
import com.golovko.adminpanel.dto.product.ProductCreateDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;
import com.golovko.adminpanel.exception.EntityAlreadyExistsException;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.ProductRepository;
import com.golovko.adminpanel.repository.RepositoryHelper;
import com.golovko.adminpanel.service.ProductService;
import com.golovko.adminpanel.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RepositoryHelper repoHelper;

    @Autowired
    private TranslationService translationService;

    @Override
    public ProductReadDTO createProduct(UUID categoryId, ProductCreateDTO createDTO) {
        if (productRepository.findByCategoryIdAndOrderNumber(categoryId, createDTO.getOrderNumber()) != null) {
            throw new EntityAlreadyExistsException(Product.class, createDTO.getName());
        }

        Product product = translationService.translate(createDTO, Product.class);
        product.setCategory(repoHelper.getReferenceIfExist(Category.class, categoryId));
        product = productRepository.save(product);

        return translationService.translate(product, ProductReadDTO.class);
    }

    @Override
    public ProductReadDTO getProductByOrderNum(UUID categoryId, Integer orderNumber) {
        Product product = getRequiredProductByOrderNumber(categoryId, orderNumber);
        return translationService.translate(product, ProductReadDTO.class);
    }

    @Override
    public ProductReadDTO patchProduct(UUID categoryId, UUID id, ProductPatchDTO patchDTO) {
        Product product = getRequiredProduct(id, categoryId);

        translationService.map(patchDTO, product);
        product = productRepository.save(product);

        return translationService.translate(product, ProductReadDTO.class);
    }

    @Override
    public void deleteProduct(UUID categoryId, UUID id) {
        Product product = getRequiredProduct(id, categoryId);
        productRepository.delete(product);
    }

    @Override
    public List<ProductReadDTO> getAllProducts(UUID categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return translationService.translateList(products, ProductReadDTO.class);
    }

    private Product getRequiredProduct(UUID id, UUID categoryId) {
        return Optional.ofNullable(productRepository.findByIdAndCategoryId(id ,categoryId))
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    private Product getRequiredProductByOrderNumber(UUID categoryId, Integer orderNumber) {
        return Optional.ofNullable(productRepository.findByCategoryIdAndOrderNumber(categoryId, orderNumber))
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Product with orderNumber %d in category %s not found", orderNumber, categoryId)));
    }
}
