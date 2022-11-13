package es.module2.smapi.controller;

import es.module2.smapi.entities.HealthStatus;
import es.module2.smapi.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping()
    public HealthStatus getHealthStatus() {
        return healthService.getHealthStatus();
    }

}
