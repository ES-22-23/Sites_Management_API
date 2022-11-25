package es.module2.smapi.entities;

import lombok.Data;

@Data
public class HealthStatus {

    private final boolean isAvailable;
    private final boolean databaseAvailable;

}
