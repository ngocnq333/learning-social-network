package com.solution.ntq.security.config;

import com.solution.ntq.security.filter.RequestAuthenticationFilter;
import com.solution.ntq.service.base.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author  Nam truong
 * @since 16/Aug/2019
 */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private TokenService tokenService;
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/v1/google-link", "/api/v1/login","/login-google").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new RequestAuthenticationFilter(tokenService, env), UsernamePasswordAuthenticationFilter.class)
                .cors();
    }
}