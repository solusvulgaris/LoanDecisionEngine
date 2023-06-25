package com.ak.loanengine.loanengine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = "User does not exist"
)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }
}
