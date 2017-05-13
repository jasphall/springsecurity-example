package com.sikorski.config.security;

import com.sikorski.config.social.SocialConnectionSignup;
import com.sikorski.config.social.SocialSignInAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ComponentScan(basePackages = { "com.sikorski" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private SocialConnectionSignup facebookConnectionSignup;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder("salt", 50000, 300);
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(facebookConnectionSignup);

        return new ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                new SocialSignInAdapter());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/resources/**")
                        .permitAll()
                    .antMatchers("/login*","/signin/**","/signup/**")
                        .permitAll()
                    .antMatchers("/api/anonymous", "/custom_form.html")
                        .permitAll()
                    .antMatchers("/api/user")
                        .hasRole("USER")
                    .antMatchers("/api/admin")
                        .hasRole("ADMIN")
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