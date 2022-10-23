package es.module2.smapi.property;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
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

import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ControllerTest {

/* 
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyRepository repository;

    @Autowired
    private OwnerRepository oRepository;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
        oRepository.deleteAll();
    }
        


    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception {
        Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        mvc.perform(post("/newProperty").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(prop1)));

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getAddress).containsOnly(prop1.getAddress());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception {
        Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        repository.save(prop1);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getName).containsOnly(prop1.getName());

        prop1.setName("Psicologia");
        mvc.perform(post("/updateProperty").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(prop1)));
        List<Property> found2 = repository.findAll();
        assertThat(found2).extracting(Property::getName).containsOnly(prop1.getName());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenDeleteProperty() throws IOException, Exception {
        Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));


        repository.save(prop1);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getId).containsOnly(prop1.getId());

        mvc.perform(post("/deleteProperty?id="+prop1.getId()));
        List<Property> found2 = repository.findAll();
        assertThat(found2 == null);
        repository.deleteAll();
    }

    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception {
        Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        repository.save(prop1);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getName).containsOnly("DETI");


         mvc.perform(get("/getProperty?address="+prop1.getAddress()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("DETI")));
        repository.deleteAll();
    }
	
	*/

 }
