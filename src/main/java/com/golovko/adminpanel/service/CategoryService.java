package com.golovko.adminpanel.service;

import com.golovko.adminpanel.dto.category.CategoryCreateDTO;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.category.CategoryReadDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryReadDTO> getAllCategories();

    List<CategoryReadDTO> createCategory(CategoryCreateDTO createDTO);

    List<CategoryReadDTO> patchCategory(UUID id, CategoryPatchDTO patchDTO);

    void deleteCategory(UUID id);

    CategoryReadDTO getCategory(UUID id);
}
