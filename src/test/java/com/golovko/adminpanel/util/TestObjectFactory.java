package com.golovko.adminpanel.util;

import com.golovko.adminpanel.domain.AppUser;
import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.repository.AppUserRepository;
import com.golovko.adminpanel.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestObjectFactory {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public AppUser createUser() {
        AppUser user = new AppUser();
        user.setUsername("username");
        user.setEncodedPassword("23432432");
        user.setIsBlocked(false);
        return appUserRepository.save(user);
    }

    public Category createCategory() {
        Category category = new Category();
        category.setName("shoes");
        return categoryRepository.save(category);
    }
}
