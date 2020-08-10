package com.golovko.adminpanel.service;

import com.golovko.adminpanel.dto.product.ProductCreateDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductReadDTO createProduct(UUID categoryId, ProductCreateDTO createDTO);

    ProductReadDTO getProduct(UUID categoryId, UUID id);

    ProductReadDTO patchProduct(UUID categoryId, UUID id, ProductPatchDTO patchDTO);

    void deleteProduct(UUID categoryId, UUID id);

    List<ProductReadDTO> getAllProducts(UUID categoryId);
}
