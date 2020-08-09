package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.AppUser;
import com.golovko.adminpanel.dto.user.AppUserCreateDTO;
import com.golovko.adminpanel.dto.user.AppUserPatchDTO;
import com.golovko.adminpanel.dto.user.AppUserReadDTO;
import com.golovko.adminpanel.exception.EntityAlreadyExistsException;
import com.golovko.adminpanel.repository.AppUserRepository;
import com.golovko.adminpanel.repository.RepositoryHelper;
import com.golovko.adminpanel.service.AppUserService;
import com.golovko.adminpanel.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private RepositoryHelper repoHelper;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TranslationService translationService;

    @Override
    public AppUserReadDTO getUser(UUID id) {
        AppUser user = repoHelper.getEntityById(AppUser.class, id);
        return translationService.translate(user, AppUserReadDTO.class);
    }

    @Override
    public List<AppUserReadDTO> getAllUsers() {
        List<AppUser> users = appUserRepository.getAllUsers();
        return translationService.translateList(users, AppUserReadDTO.class);
    }

    @Override
    public AppUserReadDTO createUser(AppUserCreateDTO createDTO) {
        if (appUserRepository.existsByUsername(createDTO.getUsername())) {
            throw new EntityAlreadyExistsException(AppUser.class, createDTO.getUsername());
        }

        AppUser user = translationService.translate(createDTO, AppUser.class);
        // todo encode password

        user = appUserRepository.save(user);

        return translationService.translate(user, AppUserReadDTO.class);
    }

    @Override
    public AppUserReadDTO patchUser(UUID id, AppUserPatchDTO patchDTO) {
        AppUser user = repoHelper.getEntityById(AppUser.class, id);

        translationService.map(patchDTO, user);
        user = appUserRepository.save(user);

        return translationService.translate(user, AppUserReadDTO.class);
    }

    @Override
    public void deleteUser(UUID id) {
        AppUser user = repoHelper.getEntityById(AppUser.class, id);
        appUserRepository.delete(user);
    }
}
