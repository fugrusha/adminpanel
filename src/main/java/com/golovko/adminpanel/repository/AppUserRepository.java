package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.domain.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, UUID> {

    @Query("from AppUser")
    List<AppUser> getAllUsers();

    boolean existsByUsername(String username);
}
