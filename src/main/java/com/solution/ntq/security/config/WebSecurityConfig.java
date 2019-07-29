package com.solution.ntq.security.config;/*
package com.solution.ntq.security.config;

import com.solution.ntq.security.filter.RequestAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


*/
/**
 * @author  Created by Ngo Quy Ngoc on 23/07/2019.
 *//*

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/login","/login-google").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new RequestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
*/
