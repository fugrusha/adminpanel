package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.AppUser;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AppUserRepositoryTest extends BaseTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void testGetAllUsers() {
        AppUser u1 = testObjectFactory.createUser();
        AppUser u2 = testObjectFactory.createUser();
        AppUser u3 = testObjectFactory.createUser();
        AppUser u4 = testObjectFactory.createUser();

        List<AppUser> actualResult = appUserRepository.getAllUsers();

        Assertions.assertThat(actualResult).extracting("id")
                .containsExactlyInAnyOrder(u1.getId(), u2.getId(), u3.getId(), u4.getId());
    }

    @Test
    public void testExistsByUserName() {
        AppUser u1 = testObjectFactory.createUser();

        Assert.assertTrue(appUserRepository.existsByUsername(u1.getUsername()));
        Assert.assertFalse(appUserRepository.existsByUsername("random name"));
    }
}
