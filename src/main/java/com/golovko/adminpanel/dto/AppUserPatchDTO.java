package com.golovko.adminpanel.dto;

import lombok.Data;

@Data
public class AppUserPatchDTO {

    private String username;

    private Boolean isBlocked;
}
