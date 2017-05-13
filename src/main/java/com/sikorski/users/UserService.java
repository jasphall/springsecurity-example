package com.sikorski.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Jan");
        userDTO.setLastname("Kowalski");
        userDTO.setEmail("jk@wp.pl");
        userDTO.setPassword("123");
        userDTO.setMatchingPassword("123");

        registerNewUserAccount(userDTO);
        System.out.println("Użytkownik jk@wp.pl stworzony.");
    }

    public User registerNewUserAccount(final UserDTO accountDto) {
        final User user = new User();

        String pass = passwordEncoder.encode(accountDto.getPassword());

        user.setFirstName(accountDto.getFirstname());
        user.setLastName(accountDto.getLastname());
        user.setPassword(pass);
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList(Role.ROLE_ADMIN));

        System.out.println("Zahashowane hasło: " + pass);

        return userRepository.save(user);
    }

}
