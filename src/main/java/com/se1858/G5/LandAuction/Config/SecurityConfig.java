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
                .antMatchers("/", "/home", "/forgot-password", "/reset-password**", "/assets/**", "/css/**", "/js/**").permitAll() // Cho phép truy cập công khai vào tài nguyên tĩnh
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/customer/profile/**", "/customer/change-password/**", "/customer/edit-profile/**","/customer/display/**").hasRole("CUSTOMER")
                .antMatchers("/admin/manage-account/**", "/admin/dashboard/**", "/admin/create-account/**").hasRole("ADMIN")
                .antMatchers("/", "/home", "/forgot-password", "/reset-password**", "/css/**", "/js/**", "/assets/**").permitAll()
                .antMatchers("/login", "/register","/auctionDetailPage").permitAll()
                .antMatchers("/customer/profile/**").hasRole("CUSTOMER")
                .antMatchers("/customer/wishlistPage/**").hasRole("CUSTOMER")
                .antMatchers("/customer/listAuctionRegister/**").hasRole("CUSTOMER")
                .antMatchers("/customer/bidPage/**").hasRole("CUSTOMER")
                .antMatchers("/customer/change-password/**").hasRole("CUSTOMER")
                .antMatchers("/customer/edit-profile/**").hasRole("CUSTOMER")
                .antMatchers("/admin/manage-account/**").hasRole("ADMIN")
                .antMatchers("/admin/dashboard/**").hasRole("ADMIN")
                .antMatchers("/admin/create-account/**").hasRole("ADMIN")
                .antMatchers("/staff/**").hasRole("STAFF")
                .antMatchers("/customer-care/**").hasRole("CUSTOMER_CARE")
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
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
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
