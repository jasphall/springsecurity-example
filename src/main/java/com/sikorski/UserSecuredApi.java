package com.sikorski;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserSecuredApi {

    @GetMapping
    public String hello() {
        return "Hello, user!";
    }

}
