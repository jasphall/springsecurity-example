package com.sikorski.config.social;

import com.sikorski.users.User;
import com.sikorski.users.UserDTO;
import com.sikorski.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;

@Component
public class SocialConnectionSignup implements ConnectionSignUp {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        logger.info("--- sign up ---");

        String firstname;
        String lastname;
        String email;
        String password = "sample_pass";

        if (connection.getKey().getProviderId().equals("social")) {
            Facebook facebook = (Facebook) connection.getApi();
            String[] fields = { "id", "email", "first_name", "last_name" };
            org.springframework.social.facebook.api.User userProfile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);

            firstname = userProfile.getFirstName();
            lastname = userProfile.getLastName();
            email = userProfile.getEmail();
        } else {
            UserProfile userProfile = connection.fetchUserProfile();

            firstname = userProfile.getFirstName();
            lastname = userProfile.getLastName();
            email = userProfile.getEmail();
        }

        logger.info("Imię: {}.", firstname);
        logger.info("Nazwisko: {}.", lastname);
        logger.info("Email: {}.", email);

        final UserDTO userDTO = new UserDTO(firstname, lastname, password, password, email);
        User user = userService.registerNewUserAccount(userDTO);

        return user.getEmail();
    }

}
