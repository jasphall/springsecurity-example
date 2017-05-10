package com.sikorski;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anonymous")
public class AnonymousApi {

    @GetMapping
    public String hello() {
        return "Hello, anonymous!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/v2")
    public String helloSecured() {
        return "Hello, anonymous from secured method!";
    }

}
