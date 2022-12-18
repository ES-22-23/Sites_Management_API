package es.module2.smapi.health;

import es.module2.smapi.service.HealthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HealthServiceTest {

    @Spy
    public HealthService healthService;

    @Test
    @DisplayName("Test getHealthService() method")
    void testGetHealthService() {
        Assertions.assertThat(healthService.getHealthStatus())
                .isNotNull()
                .hasFieldOrPropertyWithValue("isHealthy", true);
    }

}
