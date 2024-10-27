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
            error = "Password or Email is wrong";
        } else {
            error = "Password or Email is wrong";
        }
        // Đưa lỗi vào session
        request.getSession().setAttribute("loginError", error);

        // Chuyển hướng về trang login
        getRedirectStrategy().sendRedirect(request, response, "/login");
    }


}
