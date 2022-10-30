package es.module2.smapi.owner;

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
import es.module2.smapi.datamodel.OwnerDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.repository.OwnerRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;



@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class OwnerControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OwnerRepository repository;

    Owner bob;
    Owner alex;

    OwnerDTO bobDTO;
    OwnerDTO alexDTO;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        bobDTO = buildOwnerDTO(1);
        alexDTO = buildOwnerDTO(2);

        bob = buildOwnerObject(1);
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
     void whenValidInputThenCreateOwner() throws IOException, Exception {

        given().contentType(ContentType.JSON).body(alexDTO)
        .post("/owners/newOwner")
        .then().log().body().assertThat()
        .status(HttpStatus.CREATED).and()
        .contentType(ContentType.JSON).and()
        .body("username", is(alexDTO.getUsername()));
    }


    @Test
    void whenValidInputThenUpdateOwner() throws IOException, Exception {

        OwnerDTO updatedBobDTO = buildOwnerDTO(1);
        updatedBobDTO.setEmail("updatedemail1");

        given().contentType(ContentType.JSON).body(updatedBobDTO)
        .post("/owners/updateOwner")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("email", is(updatedBobDTO.getEmail())).and()
        .body("username", is(updatedBobDTO.getUsername()));
    }


    @Test
    void whenValidInputThenDeleteOwner() throws IOException, Exception {

        given().contentType(ContentType.JSON).body(bobDTO.getUsername())
        .delete("/owners/deleteOwner")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON);
    }

    @Test
     void whenFindAlexByUsernameThenReturnAlexOwner() throws IOException, Exception {

        given().get("/owners/getOwner?username="+bobDTO.getUsername())
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("username", is(bobDTO.getUsername())).and()
        .body("email", is(bobDTO.getEmail()));

    }

    OwnerDTO buildOwnerDTO(long id){
        OwnerDTO ow = new OwnerDTO();
        ow.setName("name"+id);
        ow.setEmail("email"+id);
        ow.setUsername("username"+id);
        return ow;
    }

    Owner buildOwnerObject(long id){
        Owner ow = new Owner();
        ow.setName("name"+id);
        ow.setEmail("email"+id);
        ow.setUsername("username"+id);
        return repository.saveAndFlush(ow);
    }
 }
