package com.golovko.adminpanel.service;

import com.golovko.adminpanel.dto.AppUserCreateDTO;
import com.golovko.adminpanel.dto.AppUserPatchDTO;
import com.golovko.adminpanel.dto.AppUserReadDTO;

import java.util.List;
import java.util.UUID;

public interface AppUserService {

    AppUserReadDTO getUser(UUID id);

    List<AppUserReadDTO> getAllUsers();

    AppUserReadDTO createUser(AppUserCreateDTO createDTO);

    AppUserReadDTO patchUser(UUID id, AppUserPatchDTO patchDTO);

    void deleteUser(UUID id);
}
