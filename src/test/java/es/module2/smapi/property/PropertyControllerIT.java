package es.module2.smapi.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.HttpStatus;
import io.restassured.http.ContentType;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Owner;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.repository.OwnerRepository;
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
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        // mvc.perform(post("/newProperty").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(prop1)));

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getAddress).containsOnly(prop1.getAddress());
        // repository.deleteAll();
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
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        // mvc.perform(post("/newProperty").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(prop1)));

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getAddress).containsOnly(prop1.getAddress());
        // repository.deleteAll();
        given().contentType(ContentType.JSON).body(propDTO1)
        .post("/properties/newProperty")
        .then().log().body().assertThat()
        .status(HttpStatus.BAD_REQUEST);
    }


    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception {
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        // repository.save(prop1);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getName).containsOnly(prop1.getName());

        // prop1.setName("Psicologia");
        // mvc.perform(post("/updateProperty").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(prop1)));
        // List<Property> found2 = repository.findAll();
        // assertThat(found2).extracting(Property::getName).containsOnly(prop1.getName());
        // repository.deleteAll();

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
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));


        // repository.save(prop1);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop1.getId());

        // mvc.perform(post("/deleteProperty?id="+prop1.getId()));
        // List<Property> found2 = repository.findAll();
        // assertThat(found2 == null);
        // repository.deleteAll();

        given().contentType(ContentType.JSON)
        .delete("/properties/deleteProperty?name="+propDTO2.getName()+"&address="+propDTO2.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.OK);

        //assertThat(repository.findByNameAndAddress(prop2.getName(),prop2.getAddress())).isNull();
    }

    @Test
    void whenDeleteInValidInputThenNotFOund() throws IOException, Exception {
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));


        // repository.save(prop1);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop1.getId());

        // mvc.perform(post("/deleteProperty?id="+prop1.getId()));
        // List<Property> found2 = repository.findAll();
        // assertThat(found2 == null);
        // repository.deleteAll();

        given().contentType(ContentType.JSON)
        .delete("/properties/deleteProperty?name="+propDTO3.getName()+"&address="+propDTO2.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.NOT_FOUND);

        //assertThat(repository.findByNameAndAddress(prop2.getName(),prop2.getAddress())).isNull();
    }

    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception {

        given().get("/properties/getProperty?name="+prop1.getName()+"&address="+prop1.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("name", is(prop1.getName())).and()
        .body("address", is(prop1.getAddress()));

        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        // repository.save(prop1);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getName).containsOnly("DETI");


        //  mvc.perform(get("/getProperty?name="+prop1.getName()+"&address="+prop1.getAddress()).contentType(MediaType.APPLICATION_JSON))
        //         .andDo(print())
        //         .andExpect(status().isOk())
        //         .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        //         .andExpect(jsonPath("$.name", is("DETI")));
        // repository.deleteAll();
    }

    // @Test
    // void test_getAllAdresses() throws Exception{

    //     given().get("/api/addresses")
    //     .then().log().body().assertThat()
    //     .contentType(ContentType.JSON).and()
    //     .status(HttpStatus.OK).and()
    //     .body("size", is(2)).and()
    //     .body("[0].street", is(address1.getStreet())).and()
    //     .body("[0].latitude", is((float) address1.getLatitude())).and()
    //     .body("[0].longitude", is((float) address1.getLongitude())).and()
    //     .body("[1].street", is(address2.getStreet())).and()
    //     .body("[1].latitude", is((float) address2.getLatitude())).and()
    //     .body("[1].longitude", is((float) address2.getLongitude()));
    // }

    Property buildPropertyObject(long id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"name"+id);
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
