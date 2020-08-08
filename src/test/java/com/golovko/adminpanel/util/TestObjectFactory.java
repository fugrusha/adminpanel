package com.golovko.adminpanel.util;

import com.golovko.adminpanel.domain.AppUser;
import com.golovko.adminpanel.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestObjectFactory {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser createUser() {
        AppUser user = new AppUser();
        user.setUsername("username");
        user.setEncodedPassword("23432432");
        user.setIsBlocked(false);
        return appUserRepository.save(user);
    }
}
