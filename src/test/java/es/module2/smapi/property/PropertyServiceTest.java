package es.module2.smapi.property;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;

import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.service.SMAPIService;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.PropertyRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PropertyServiceTest {


    @Autowired
    private PropertyRepository repository;
    
    @Autowired
    private SMAPIService service;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception {
        Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        service.createProperty(prop1);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getId).containsOnly(prop1.getId());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception {
        Property prop2 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));


        repository.save(prop2);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getId).containsOnly(prop2.getId());

        prop2.setAddress("address2");
        service.updateProperty(prop2);
        List<Property> found2 = repository.findAll();
        assertThat(found2).extracting(Property::getAddress).containsOnly(prop2.getAddress());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenDeleteProperty() throws IOException, Exception {
        Property prop3 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));


        repository.save(prop3);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getId).containsOnly(prop3.getId());

        service.deleteProperty(prop3.getId());
        List<Property> found2 = repository.findAll();
        assertThat(found2 == null);
        repository.deleteAll();
    }

    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception {
        Property prop4 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        repository.save(prop4);

        List<Property> found = repository.findAll();
        assertThat(found).extracting(Property::getId).containsOnly(prop4.getId());


        Property found2= service.getProperty(prop4.getAddress());

        assertThat(found.equals(found2));
        repository.deleteAll();
    }
	
	
	

 }
