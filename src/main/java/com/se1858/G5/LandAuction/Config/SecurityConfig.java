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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home","/forgot-password", "/reset-password**","/register","/verify-otp", "/css/**", "/js/**", "/assets/**",
                "/doc/**","/coffeescripts/**","/icon/**","/images/**","/Land_images/**"
                ,"/News_images/**","/User_images/**","/transfonts/**")
                .permitAll()
                .antMatchers("/auction/showAuctions/**","/news/**", "/aboutUs")
                .access("!hasRole('ADMIN') and !hasRole('CUSTOMER_CARE') and !hasRole('STAFF')")
                .antMatchers("/profile/**","/profile").hasRole("CUSTOMER")
                .antMatchers("/auction/showAuctionResults").hasRole("STAFF")
                .antMatchers("/changePassword",  "/customer/display/**").hasRole("CUSTOMER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(("/auction/showAuctionDetail/**")).hasRole("CUSTOMER")
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                .antMatchers("/wishlist/**").hasRole("CUSTOMER")
                .antMatchers("/asset/**").hasRole("CUSTOMER")
                .antMatchers("/auctionRegistration/showAuctionRegistrationPage").hasRole("CUSTOMER")
                .antMatchers("/staff").hasRole("STAFF")
                .antMatchers("/customer-care/**","customer-care**")
                .hasRole("CUSTOMER_CARE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successForwardUrl("/home")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(1209600) // 2 tuần
                .key("uniqueAndSecret")
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
    public HttpFirewall allowUrlEncodedDoubleSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // Cho phép chuỗi "//"
        return firewall;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
