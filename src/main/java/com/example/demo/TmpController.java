package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TmpController {
    @GetMapping("/api/tmp")
    public String showLoginForm() {
        return "tmp";
    }
}
