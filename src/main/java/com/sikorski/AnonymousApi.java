package com.sikorski;

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

}
