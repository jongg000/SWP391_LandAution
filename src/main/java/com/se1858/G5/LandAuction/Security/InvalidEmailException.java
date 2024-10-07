package com.se1858.G5.LandAuction.Security;


import org.springframework.security.core.AuthenticationException;

public class InvalidEmailException extends AuthenticationException {
    public InvalidEmailException(String msg) {
        super(msg);
    }
}
