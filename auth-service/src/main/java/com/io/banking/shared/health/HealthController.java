package com.io.banking.shared.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Server is up and running successfully!";
    }

    @GetMapping("/version")
    public String version() {
        return "2.0";
    }
}
