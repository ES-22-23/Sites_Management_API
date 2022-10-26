package es.module2.smapi.property;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import org.junit.jupiter.api.AfterEach;

import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.service.PropertyService;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.PropertyRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {


    @Mock(lenient = true)
    private PropertyRepository repository;
    
    @InjectMocks
    private PropertyService service;


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


    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception {
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        // service.createProperty(prop1);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop1.getId());
        // repository.deleteAll();

        Mockito.when(repository.saveAndFlush(any(Property.class))).thenReturn(prop1);

        Property result = service.createProperty(propDTO1);

        assertEquals(prop1, result);
    }


    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception {
        // Property prop2 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));


        // repository.save(prop2);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop2.getId());

        // prop2.setAddress("address2");
        // service.updateProperty(prop2);
        // List<Property> found2 = repository.findAll();
        // assertThat(found2).extracting(Property::getAddress).containsOnly(prop2.getAddress());
        // repository.deleteAll();

        Mockito.when(repository.saveAndFlush(any(Property.class))).thenReturn(prop1);

        Property result = service.updateProperty(propDTO1);

        assertTrue(prop1.equals(result));
    }



    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception {
        // Property prop4 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        // repository.save(prop4);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop4.getId());


        Property found= service.getProperty(prop2.getName(), prop2.getAddress());

        assertThat(found).isNotNull();
        assertThat(found.equals(prop2));
    }
	
	
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
