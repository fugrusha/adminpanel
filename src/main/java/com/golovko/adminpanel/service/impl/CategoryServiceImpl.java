package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.dto.category.CategoryCreateDTO;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.category.CategoryReadDTO;
import com.golovko.adminpanel.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<CategoryReadDTO> getAllCategories() {
        return null;
    }

    @Override
    public List<CategoryReadDTO> createCategory(CategoryCreateDTO createDTO) {
        return null;
    }

    @Override
    public List<CategoryReadDTO> patchCategory(UUID id, CategoryPatchDTO patchDTO) {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {

    }
}
