package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.dto.category.CategoryCreateDTO;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.category.CategoryReadDTO;
import com.golovko.adminpanel.exception.EntityAlreadyExistsException;
import com.golovko.adminpanel.repository.CategoryRepository;
import com.golovko.adminpanel.repository.RepositoryHelper;
import com.golovko.adminpanel.service.CategoryService;
import com.golovko.adminpanel.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private RepositoryHelper repoHelper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TranslationService translationService;

    @Override
    public List<CategoryReadDTO> getAllCategories() {
        List<Category> categories = categoryRepository.getAllCategories();
        return translationService.translateList(categories, CategoryReadDTO.class);
    }

    @Override
    public List<CategoryReadDTO> createCategory(CategoryCreateDTO createDTO) {
        if (categoryRepository.existsByName(createDTO.getName())) {
            throw new EntityAlreadyExistsException(Category.class, createDTO.getName());
        }

        Category category = translationService.translate(createDTO, Category.class);
        categoryRepository.save(category);

        List<Category> categories = categoryRepository.getAllCategories();
        return translationService.translateList(categories, CategoryReadDTO.class);
    }

    @Override
    public List<CategoryReadDTO> patchCategory(UUID id, CategoryPatchDTO patchDTO) {
        Category category = repoHelper.getEntityById(Category.class, id);

        translationService.map(patchDTO, category);
        categoryRepository.save(category);

        List<Category> categories = categoryRepository.getAllCategories();
        return translationService.translateList(categories, CategoryReadDTO.class);
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = repoHelper.getEntityById(Category.class, id);
        categoryRepository.delete(category);
    }
}
