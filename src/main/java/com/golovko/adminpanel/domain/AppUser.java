package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class AppUser extends AbstractEntity {

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String chatId;

    private String encodedPassword;

    private Boolean isBlocked = true;
}
