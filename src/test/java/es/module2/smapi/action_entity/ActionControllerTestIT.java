package es.module2.smapi.action_entity;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.model.Action;
import es.module2.smapi.repository.ActionRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ActionControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ActionRepository repository;

    @Autowired
    private EntityManager entityManager;

    Action action1, action2, action3;

    @BeforeEach
    void setUp() throws JsonProcessingException{
        entityManager.clear();

        RestAssuredMockMvc.mockMvc( mvc );

        action1 = new Action("2022-12-03 17:16:07.657", "Admin1", "CREATE", "Owner", "owner1");
        action2 = new Action("2022-12-03 17:16:07.657", "Admin2", "DELETE", "Property", "2");
        action3 = new Action("2022-12-03 17:16:07.657", "Admin3", "UPDATE", "Canera", "1");

        action1 = repository.saveAndFlush(action1);
        action2 = repository.saveAndFlush(action2);
        action3 = repository.saveAndFlush(action3);


    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
        entityManager.clear();
    }

    @Test
     void whenGetAllAlarmsThenReturnAllAlarms() throws IOException, Exception {
        given().get("/actions")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0].admin", is(action1.getAdmin())).and()
        .body("[1].admin", is(action2.getAdmin())).and()
        .body("[2].admin", is(action3.getAdmin()));
    }
}
