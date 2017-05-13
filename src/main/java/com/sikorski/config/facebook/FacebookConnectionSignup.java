package com.sikorski.config.facebook;

import com.sikorski.users.User;
import com.sikorski.users.UserDTO;
import com.sikorski.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;

@Component
public class FacebookConnectionSignup implements ConnectionSignUp {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        logger.info("--- sign up ---");

        Facebook facebook = (Facebook) connection.getApi();
        String[] fields = { "id", "email", "first_name", "last_name" };
        org.springframework.social.facebook.api.User userProfile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);

        logger.info("Imię: {}.", userProfile.getFirstName());
        logger.info("Nazwisko: {}.", userProfile.getLastName());
        logger.info("Email: {}.", userProfile.getEmail());

        String firstname = userProfile.getFirstName();
        String lastname = userProfile.getLastName();
        String email = userProfile.getEmail();
        String password = "sample_pass";

        final UserDTO userDTO = new UserDTO(firstname, lastname, password, password, email);
        User user = userService.registerNewUserAccount(userDTO);

        return user.getEmail();
    }

}
