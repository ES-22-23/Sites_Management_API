package es.module2.smapi.service;

import es.module2.smapi.entities.HealthStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class HealthService {

    public HealthStatus getHealthStatus() {
        return new HealthStatus(true, new ArrayList<>());
    }

}
