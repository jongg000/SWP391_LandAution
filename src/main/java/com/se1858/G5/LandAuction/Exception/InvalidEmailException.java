package com.se1858.G5.LandAuction.Exception;


import org.springframework.security.core.AuthenticationException;

public class InvalidEmailException extends AuthenticationException {
    public InvalidEmailException(String msg) {
        super(msg);
    }
}
