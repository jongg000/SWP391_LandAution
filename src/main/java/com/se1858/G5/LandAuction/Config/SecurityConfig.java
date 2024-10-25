package com.se1858.G5.LandAuction.Config;

import com.se1858.G5.LandAuction.Security.CustomAuthenticationFailureHandler;
import com.se1858.G5.LandAuction.Security.CustomAuthenticationSuccessHandler;
import com.se1858.G5.LandAuction.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/","/home", "/css/**", "/js/**", "/assets/**").permitAll()
                .antMatchers("/login","/register").permitAll()
                .antMatchers("/profile", "/changePassword","/editProfile").authenticated()
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/staff/**").hasRole("STAFF")
                .antMatchers("/customer-care/**").hasRole("CUSTOMER_CARE")
                .antMatchers("/profile").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successForwardUrl("/home")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .logout()
                .invalidateHttpSession(true)  // Xóa session hiện tại
                .clearAuthentication(true)    // Xóa thông tin xác thực
                .deleteCookies("JSESSIONID")  // Xóa cookie session
                .logoutUrl("/logout")         // URL để logout
                .logoutSuccessUrl("/login?logout")  // Chuyển hướng sau khi logout thành công
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
