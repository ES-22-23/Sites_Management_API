package es.module2.smapi.health;

import es.module2.smapi.SmapiApplication;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
public class HealthControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void testGetHealthCheck() {
        given().get("/health")
                .then().log().body().assertThat()
                .status(HttpStatus.OK);
    }

}
