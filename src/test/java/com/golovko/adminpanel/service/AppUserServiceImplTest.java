package com.golovko.adminpanel.service;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.AppUser;
import com.golovko.adminpanel.dto.user.AppUserCreateDTO;
import com.golovko.adminpanel.dto.user.AppUserPatchDTO;
import com.golovko.adminpanel.dto.user.AppUserReadDTO;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.AppUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class AppUserServiceImplTest extends BaseTest {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void testGetUser() {
        AppUser user = testObjectFactory.createUser();

        AppUserReadDTO actualResult = appUserService.getUser(user.getId());

        Assertions.assertThat(user).isEqualToIgnoringGivenFields(actualResult,
                "encodedPassword");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserWrongId() {
        appUserService.getUser(UUID.randomUUID());
    }

    @Test
    public void testGetAllUsers() {
        AppUser u1 = testObjectFactory.createUser();
        AppUser u2 = testObjectFactory.createUser();
        AppUser u3 = testObjectFactory.createUser();

        List<AppUserReadDTO> allUsers = appUserService.getAllUsers();

        Assertions.assertThat(allUsers).extracting("id")
                .containsExactlyInAnyOrder(u1.getId(), u2.getId(), u3.getId());
    }

    @Test
    public void testCreateUser() {
        AppUserCreateDTO createDTO = new AppUserCreateDTO();
        createDTO.setUsername("maximus");
        createDTO.setPassword("123435");
        createDTO.setPasswordConfirmation("123435");

        AppUserReadDTO createdUser = appUserService.createUser(createDTO);

        Assert.assertEquals(createDTO.getUsername(), createdUser.getUsername());
        Assert.assertTrue(createdUser.getIsBlocked());
    }

    @Test
    public void testPatchUser() {
        AppUser u1 = testObjectFactory.createUser();

        AppUserPatchDTO patchDTO = new AppUserPatchDTO();
        patchDTO.setIsBlocked(true);
        patchDTO.setUsername("new username");

        AppUserReadDTO actualResult = appUserService.patchUser(u1.getId(), patchDTO);

        Assertions.assertThat(patchDTO).isEqualToComparingFieldByField(actualResult);
    }

    @Test
    public void testDeleteUser() {
        AppUser u1 = testObjectFactory.createUser();

        appUserService.deleteUser(u1.getId());

        Assert.assertFalse(appUserRepository.existsById(u1.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteUserNotExist() {
        appUserService.deleteUser(UUID.randomUUID());
    }
}
