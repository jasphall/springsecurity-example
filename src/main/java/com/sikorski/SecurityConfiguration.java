package com.sikorski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // sample users
    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles(Role.USER.toString())
                .and()
                .withUser("admin").password("admin").roles(Role.USER.toString(), Role.ADMIN.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/resources/**")
                        .permitAll()
                    .antMatchers("/api/anonymous")
                        .permitAll()
                    .antMatchers("/api/user")
                        .hasRole(Role.USER.toString())
                    .antMatchers("/api/admin")
                        .hasRole(Role.ADMIN.toString())
                    .anyRequest()
                        .authenticated()
                .and()
                    .formLogin()
                        .permitAll()
                        .defaultSuccessUrl("/hello")
                .and()
                    .logout()
                        .permitAll()
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/login")
                        .logoutUrl("/logout");
    }
}