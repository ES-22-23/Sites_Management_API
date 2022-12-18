package es.module2.smapi.entities;

import lombok.Data;
import java.util.List;

@Data
public class HealthStatus {

    private final boolean isHealthy;
    private final List<?> additionalProperties;

}
