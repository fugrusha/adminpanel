package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.dto.product.ProductCreateDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;
import com.golovko.adminpanel.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductReadDTO createProduct(UUID categoryId, ProductCreateDTO createDTO) {
        return null;
    }

    @Override
    public ProductReadDTO getProductByOrderNum(UUID categoryId, Integer orderNumber) {
        return null;
    }

    @Override
    public ProductReadDTO patchProduct(UUID categoryId, UUID id, ProductPatchDTO patchDTO) {
        return null;
    }

    @Override
    public void deleteProduct(UUID categoryId, UUID id) {

    }

    @Override
    public List<ProductReadDTO> getAllProducts(UUID categoryId) {
        return null;
    }
}
