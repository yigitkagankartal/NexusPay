package com.nexuspay.notification_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        return Map.of(
                "service", "Notification Service",
                "status", "UP",
                "version", "v1"
        );
    }
}