package es.module2.smapi.alarm;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

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
import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AlarmControllerIT {

    @Autowired
    private MockMvc mvc;

	@Autowired
    private AlarmRepository repository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    Alarm al1, al2, al3, al4;
    AlarmDTO alDTO1, alDTO2, alDTO3, alDTO4;
    Property prop1, prop2, prop3, prop4;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);

        al1 = buildAlarmObject(1);
        al2 = buildAlarmObject(2);
        al3 = buildAlarmObject(3);

        al1.setProperty(prop1);
        al2.setProperty(prop2);
        al3.setProperty(prop3);

        al1 = repository.saveAndFlush(al1);
        al2 = repository.saveAndFlush(al2);
        al3 = repository.saveAndFlush(al3);

        alDTO1 = buildAlarmDTO(1);
        alDTO2 = buildAlarmDTO(2);
        alDTO3 = buildAlarmDTO(3);
        alDTO4 = buildAlarmDTO(4);
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
        propertyRepository.deleteAll();
    }

    @Test
     void whenValidInputThenCreateAlarm() throws IOException, Exception {
        given().contentType(ContentType.JSON).body(alDTO4)
        .post("/alarms/newAlarm")
        .then().log().body().assertThat()
        .status(HttpStatus.CREATED).and()
        .contentType(ContentType.JSON).and()
        .body("privateId", is((int) alDTO4.getPrivateId()));
        
    }
    @Test
     void whenInvalidInputThenNotCreateAlarm() throws IOException, Exception {
        given().contentType(ContentType.JSON).body(alDTO1)
        .post("/alarms/newAlarm")
        .then().log().body().assertThat()
        .status(HttpStatus.BAD_REQUEST);
        
    }
    /*@Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception {

        alDTO2.setPropertyName("name3");
        alDTO2.setPropertyAddress("address3");
        given().contentType(ContentType.JSON).body(alDTO2)
        .post("/alarms/updateAlarm")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("privateId", is(al2.getPrivateId()));

    }*/


    @Test
    void whenValidInputThenDeleteAlarm() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .delete("/alarms/deleteAlarm?id="+al2.getId())
        .then().log().body().assertThat()
        .status(HttpStatus.OK);

    }

    @Test
    void whenInvalidInputThenNotFound() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .get("/alarms/getAlarm?id="+1000)
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
        al.setId(id);
        al.setPrivateId( id);
        return al;
    }

    AlarmDTO buildAlarmDTO(long id){
        AlarmDTO al = new AlarmDTO();
        al.setPrivateId( id);
        al.setPropertyAddress("address"+id);
        al.setPropertyName("name"+id);
        return al;
    }
	
    Property buildPropertyObject(long id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"name"+id);
        ow = ownerRepository.saveAndFlush(ow);
        prop.setId(id);
        prop.setName("name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        prop = propertyRepository.saveAndFlush(prop);
        ow.getProperties().add(prop);
        return prop;
    }
 }
