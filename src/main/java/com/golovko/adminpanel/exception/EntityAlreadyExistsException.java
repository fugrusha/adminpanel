package com.golovko.adminpanel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(Class entityClass, String message) {
        super(String.format("%s with name %s already exists", entityClass, message));
    }
}
