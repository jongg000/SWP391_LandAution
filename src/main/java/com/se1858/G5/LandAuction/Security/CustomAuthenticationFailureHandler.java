
package com.se1858.G5.LandAuction.Security;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
        String error;

        if (exception instanceof UsernameNotFoundException) {
            error = "Email của bạn chưa chính xác hoặc không tồn tại";
        } else if (exception instanceof BadCredentialsException) {
            error = "Mật khẩu của bạn chưa chính xác";
        } else if (exception instanceof DisabledException) {
            error = "Tài khoản của bạn đã bị vô hiệu hóa";
        } else if (exception instanceof InternalAuthenticationServiceException && exception.getCause() instanceof DisabledException) {
            error = "Tài khoản của bạn đã bị vô hiệu hóa";
        } else {
            error = "Đăng nhập thất bại. Vui lòng thử lại.";
        }

        // Đưa lỗi vào session
        request.getSession().setAttribute("loginError", error);

        // Chuyển hướng về trang login
        getRedirectStrategy().sendRedirect(request, response, "/login");
    }



}
