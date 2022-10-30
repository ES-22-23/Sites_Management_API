package es.module2.smapi.property;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PropertyControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyRepository repository;
    
    
    @Autowired
    private OwnerRepository owRepository;

    Property prop1, prop2, prop3, prop4;
    PropertyDTO propDTO1, propDTO2, propDTO3, propDTO4;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);

        repository.saveAndFlush(prop1);
        repository.saveAndFlush(prop2);
        repository.saveAndFlush(prop3);

        propDTO1 = buildPropertyDTO(1);
        propDTO2 = buildPropertyDTO(2);
        propDTO3 = buildPropertyDTO(3);
        propDTO4 = buildPropertyDTO(4);
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
        owRepository.deleteAll();
    }
        
    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception {

        List<Owner> found = owRepository.findAll();
        assertThat(found).extracting(Owner::getUsername).contains(propDTO4.getOwnerUsername());
        assertThat(propDTO4!= null).isTrue();
        given().contentType(ContentType.JSON).body(propDTO4)
        .post("/properties/newProperty")
        .then().log().body().assertThat()
        .contentType(ContentType.JSON).and()
        .status(HttpStatus.CREATED).and()
        .body("name", is(prop4.getName())).and()
        .body("address", is(prop4.getAddress()));
        
    }

    @Test
     void whenInValidInputThenNotCreateProperty() throws IOException, Exception {

        given().contentType(ContentType.JSON).body(propDTO1)
        .post("/properties/newProperty")
        .then().log().body().assertThat()
        .status(HttpStatus.BAD_REQUEST);
    }


    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception {

        propDTO2.setOwnerUsername("username3");
        given().contentType(ContentType.JSON).body(propDTO2)
        .post("/properties/updateProperty")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("owner", is("username3")).and()
        .body("name", is(prop2.getName())).and()
        .body("address", is(prop2.getAddress()));

    }

    @Test
    void whenValidInputThenDeleteProperty() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .delete("/properties/deleteProperty?name="+propDTO2.getName()+"&address="+propDTO2.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.OK);

    }

    @Test
    void whenDeleteInValidInputThenNotFOund() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .delete("/properties/deleteProperty?name="+propDTO3.getName()+"&address="+propDTO2.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.NOT_FOUND);

    }

    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception {

        given().get("/properties/getProperty?name="+prop1.getName()+"&address="+prop1.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("name", is(prop1.getName())).and()
        .body("address", is(prop1.getAddress()));
    }

    Property buildPropertyObject(long id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"email"+id,"name"+id);
        prop.setId(id);
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        owRepository.saveAndFlush(ow);
        return prop;
    }

    PropertyDTO buildPropertyDTO(long id){
        PropertyDTO prop = new PropertyDTO();
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwnerUsername("username"+id);
        return prop;
    }

 }
