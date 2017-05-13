package com.sikorski.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        final WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        if (auth != null) {
            System.out.println("Sory, nie mogę Cię zalogować!");
        }
    }

}
