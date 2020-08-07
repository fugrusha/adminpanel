package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.product.ProductCreateDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;
import com.golovko.adminpanel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{order}")
    public ProductReadDTO getProductByOrderNum(
            @PathVariable UUID categoryId,
            @PathVariable Integer order
    ) {
        return productService.getProductByOrderNum(categoryId, order);
    }

    @GetMapping
    public List<ProductReadDTO> getAllProducts(@PathVariable UUID categoryId) {
        return productService.getAllProducts(categoryId);
    }

    @PostMapping
    public ProductReadDTO createProduct(
            @PathVariable UUID categoryId,
            @RequestBody ProductCreateDTO createDTO
    ) {
        return productService.createProduct(categoryId, createDTO);
    }

    @PatchMapping("/{id}")
    public ProductReadDTO patchProduct(
            @PathVariable UUID categoryId,
            @PathVariable UUID id,
            @RequestBody ProductPatchDTO patchDTO
    ) {
        return productService.patchProduct(categoryId, id, patchDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable UUID categoryId,
            @PathVariable UUID id
    ) {
        productService.deleteProduct(categoryId, id);
    }
}
