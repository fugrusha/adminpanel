package com.golovko.adminpanel.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AppUserReadDTO {

    private UUID id;

    private String username;

    private Boolean isBlocked;
}
