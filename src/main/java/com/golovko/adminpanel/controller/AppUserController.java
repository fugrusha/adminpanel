package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.AppUserCreateDTO;
import com.golovko.adminpanel.dto.AppUserPatchDTO;
import com.golovko.adminpanel.dto.AppUserReadDTO;
import com.golovko.adminpanel.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/{id}")
    public AppUserReadDTO getUser(@PathVariable UUID id) {
        return appUserService.getUser(id);
    }

    @GetMapping
    public List<AppUserReadDTO> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @PostMapping
    public AppUserReadDTO createUser(@RequestBody AppUserCreateDTO createDTO) {
        return appUserService.createUser(createDTO);
    }

    @PatchMapping("/{id}")
    public AppUserReadDTO patchUser(
            @PathVariable UUID id,
            @RequestBody AppUserPatchDTO patchDTO
    ) {
        return appUserService.patchUser(id, patchDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        appUserService.deleteUser(id);
    }
}
