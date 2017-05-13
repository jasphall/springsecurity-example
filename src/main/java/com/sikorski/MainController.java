package com.sikorski;

import com.sikorski.users.User;
import com.sikorski.users.UserDTO;
import com.sikorski.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "registration")
    public UserDTO getRegistrationData() {
        return new UserDTO();
    }

    @PostMapping(path = "registration")
    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
        User registered = new User();

        registered = userService.registerNewUserAccount(userDTO);

        return new UserDTO(registered);
    }

    @GetMapping(path = "hello")
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        Set<String> roles = authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toSet());

        return "Witaj, " + name + ". Twoje role to: " + roles + ".";
    }

    private User createUserAccount(UserDTO userDTO) {
        User registered = userService.registerNewUserAccount(userDTO);
        return registered;
    }

}
