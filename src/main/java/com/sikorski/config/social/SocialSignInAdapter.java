package com.sikorski.config.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;

@Service
public class SocialSignInAdapter implements SignInAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
        logger.info("--- sign in adapter ---");

        String role = "";
        String providerId = connection.getKey().getProviderId();

        switch (providerId) {
            case "social":
                role = "FACEBOOK_USER";
                break;
            case "google":
                role = "GOOGLE_USER";
                break;
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(connection.getDisplayName(),
                null,
                Arrays.asList(new SimpleGrantedAuthority(role))
        ));

        return null;
    }

}
