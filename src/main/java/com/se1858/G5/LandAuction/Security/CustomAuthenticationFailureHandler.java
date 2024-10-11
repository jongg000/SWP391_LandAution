package com.se1858.G5.LandAuction.Security;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, SecurityException {
        String error = null;


        if (exception instanceof UsernameNotFoundException) {
            // Username not found
            error = "Email is not valid";
            request.getSession().setAttribute("usernameError", error);
            getRedirectStrategy().sendRedirect(request, response, "/login?error=email");
        }else {
            error = "Password is wrong";
            request.getSession().setAttribute("passwordError", error);
            getRedirectStrategy().sendRedirect(request, response, "/login?error=EmailOrPassword");
        }

    }
}
