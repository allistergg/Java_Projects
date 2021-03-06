package com.company.AllisterRookeU1Capstone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT username, authority FROM authorities WHERE username = ?")
                .passwordEncoder(encoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.PUT, "/console/*", "/game/*", "/tshirt/*").hasAnyAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.POST, "/console", "/game", "/tshirt").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.DELETE,"/console/*", "/game/*", "/tshirt/*").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
