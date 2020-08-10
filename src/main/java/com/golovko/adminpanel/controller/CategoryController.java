package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.category.CategoryCreateDTO;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.category.CategoryReadDTO;
import com.golovko.adminpanel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryReadDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryReadDTO getCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id);
    }

    @PostMapping
    public List<CategoryReadDTO> createCategory(@RequestBody CategoryCreateDTO createDTO) {
        return categoryService.createCategory(createDTO);
    }

    @PatchMapping("/{id}")
    public List<CategoryReadDTO> patchCategory(
            @PathVariable UUID id,
            @RequestBody CategoryPatchDTO patchDTO
    ) {
        return categoryService.patchCategory(id, patchDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
