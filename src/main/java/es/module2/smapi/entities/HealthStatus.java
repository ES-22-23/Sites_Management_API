package es.module2.smapi.entities;

import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class HealthStatus {

    @JsonProperty("isHealthy")
    private final boolean isHealthy;
    private final List<?> additionalProperties;

}
