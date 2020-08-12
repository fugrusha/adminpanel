package com.golovko.adminpanel.dto.user;

import lombok.Data;

@Data
public class AppUserPatchDTO {

    private String username;

    private String chatId;

    private Boolean isBlocked;
}
