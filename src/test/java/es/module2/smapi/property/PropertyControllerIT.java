package es.module2.smapi.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import org.junit.jupiter.api.AfterEach;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Owner;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.PropertyRepository;
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


import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PropertyControllerIT {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyRepository repository;

    Property prop1, prop2, prop3, prop4;
    PropertyDTO propDTO1, propDTO2, propDTO3, propDTO4;


    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        prop1 = buildAddressObject(1);
        prop2 = buildAddressObject(2);
        prop3 = buildAddressObject(3);
        prop4 = buildAddressObject(4);

        repository.saveAndFlush(prop1);
        repository.saveAndFlush(prop2);
        repository.saveAndFlush(prop3);

        propDTO1 = buildAddressDTO(1);
        propDTO2 = buildAddressDTO(2);
        propDTO3 = buildAddressDTO(3);
        propDTO4 = buildAddressDTO(4);
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }
        


    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception {
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        // mvc.perform(post("/newProperty").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(prop1)));

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getAddress).containsOnly(prop1.getAddress());
        // repository.deleteAll();

        given().contentType(ContentType.JSON).body(propDTO4)
        .post("/newProperty")
        .then().log().body().assertThat()
        .contentType(ContentType.JSON).and()
        .status(HttpStatus.CREATED).and()
        .body("name", is(prop4.getName())).and()
        .body("address", is(prop4.getAddress())).and()
        .body("owner.name", is( prop4.getOwnerName())).and()
        .body("owner.name", is(prop4.getOwnerUsername()));
        
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

        propDTO4.setName("DETI");
        given().contentType(ContentType.JSON).body(propDTO4)
        .post("/updateProperty")
        .then().log().body().assertThat()
        .contentType(ContentType.JSON).and()
        .status(HttpStatus.CREATED).and()
        .body("name", is("DETI")).and()
        .body("address", is(prop4.getAddress())).and()
        .body("owner.name", is( prop4.getOwnerName())).and()
        .body("owner.name", is(prop4.getOwnerUsername()));

        assertThat( repository.findByAddress(prop4.getAddress()).getName().equals("DETI")).isTrue();


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
        .get("/deleteProperty?name="+prop2.getName()+"&address="+prop2.getAddress())
        .then().log().body().assertThat()
        .status(HttpStatus.DELETE);

        assertThat(repository.findByNameAndAddress(prop2.getName(),prop2.getAddress())).isNull();
    }

    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception {

        given().get("/getProperty?name="+prop1.getName()+"&address="+prop1.getAddress())
        .then().log().body().assertThat()
        .contentType(ContentType.JSON).and()
        .status(HttpStatus.OK).and()
        .body("size", is(1)).and()
        .body("[0].name", is(prop1.getName())).and()
        .body("[0].address", is(prop1.getAddress())).and()
        .body("[0].owner.name", is( prop1.getOwnerName())).and()
        .body("[0].owner.name", is(prop1.getOwnerUsername())).and();

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
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(new Owner("username"+id,"name"+id));
        return prop;
    }

    PropertyDTO buildPropertyDTO(long id){
        PropertyDTO prop = new PropertyDTO();
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(new Owner("username"+id,"name"+id));
        return prop;
    }

 }
