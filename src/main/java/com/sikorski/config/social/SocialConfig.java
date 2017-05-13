package com.sikorski.config.social;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.google.connect.GoogleConnectionFactory;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {

        connectionFactoryConfigurer.addConnectionFactory(new GoogleConnectionFactory(
                environment.getProperty("spring.social.google.appId"),
                environment.getProperty("spring.social.google.appSecret")
        ));

    }

}
