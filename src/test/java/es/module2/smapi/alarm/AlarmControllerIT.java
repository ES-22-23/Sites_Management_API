package es.module2.smapi.alarm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import es.module2.smapi.SmapiApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.HttpStatus;
import io.restassured.http.ContentType;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Owner;
import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.repository.AlarmRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import es.module2.smapi.exceptions.PropertyAlreadyExistsException;



import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AlarmControllerIT {

    @Autowired
    private MockMvc mvc;

	@Autowired
    private AlarmRepository repository;

    @Autowired
    private PropertyRepository propRepository;

    Alarm al1, al2, al3, al4;
    AlarmDTO alDTO1, alDTO2, alDTO3, alDTO4;
        
    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        al1 = buildAlarmObject(1);
        al2 = buildAlarmObject(2);
        al3 = buildAlarmObject(3);
        al4 = buildAlarmObject(4);

        repository.saveAndFlush(al1);
        repository.saveAndFlush(al2);
        repository.saveAndFlush(al3);

        alDTO1 = buildAlarmDTO(1);
        alDTO2 = buildAlarmDTO(2);
        alDTO3 = buildAlarmDTO(3);
        alDTO4 = buildAlarmDTO(4);
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
        propRepository.deleteAll();
    }

    @Test
     void whenValidInputThenCreateAlarm() throws IOException, Exception {
        given().contentType(ContentType.JSON).body(alDTO4)
        .post("/alarms/newAlarm")
        .then().log().body().assertThat()
        .status(HttpStatus.CREATED).and()
        .contentType(ContentType.JSON).and()
        .body("privateId", is(al4.getPrivateId()));
        
    }
    @Test
     void whenInValidInputThenNotCreateAlarm() throws IOException, Exception {
        given().contentType(ContentType.JSON).body(alDTO1)
        .post("/alarms/newAlarm")
        .then().log().body().assertThat()
        .status(HttpStatus.BAD_REQUEST);
        
    }
    @Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception {

        alDTO2.setPropertyName("name3");
        alDTO2.setPropertyAddress("address3");
        given().contentType(ContentType.JSON).body(alDTO2)
        .post("/alarms/updateAlarm")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("privateId", is(al2.getPrivateId()));


    }


    @Test
    void whenValidInputThenDeleteAlarm() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .delete("/alarms/deleteAlarm?id="+al2.getId())
        .then().log().body().assertThat()
        .status(HttpStatus.OK);

    }

        @Test
    void whenInValidInputThenNotFound() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .delete("/alarms/deleteAlarm?id="+1000)
        .then().log().body().assertThat()
        .status(HttpStatus.NOT_FOUND);

    }

    @Test
     void whenValidInputThenGetAlarm() throws IOException, Exception {

        given().get("/alarms/getAlarm?id="+al1.getId())
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("id", is((int)al1.getId()));

     }
    Alarm buildAlarmObject(long id){
        Alarm al = new Alarm();
        Property prop=  new Property("address"+id,"name"+id, new Owner("username"+id,"name"+id));
        al.setId(id);
        al.setPrivateId( id);
        al.setProperty(prop);
        propRepository.saveAndFlush(prop);
        return al;
    }

    AlarmDTO buildAlarmDTO(long id){
        AlarmDTO al = new AlarmDTO();
        al.setPrivateId( id);
        al.setPropertyAddress("address"+id);
        al.setPropertyName("name"+id);
        return al;
    }
 }
