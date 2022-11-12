package es.module2.smapi.service;

import es.module2.smapi.entities.HealthStatus;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthStatus getHealthStatus() {
        return new HealthStatus(true, true);
    }

}
