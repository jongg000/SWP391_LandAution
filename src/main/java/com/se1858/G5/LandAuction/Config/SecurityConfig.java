package com.se1858.G5.LandAuction.Config;


import com.se1858.G5.LandAuction.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests( authorize -> authorize
                        .requestMatchers("/login","/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin( form -> form.loginPage("/login").
                        defaultSuccessUrl("/home",true).permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager athManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder   =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(new CustomUserDetailsService())
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
