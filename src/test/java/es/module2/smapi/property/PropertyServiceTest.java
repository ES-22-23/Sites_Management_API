package es.module2.smapi.property;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import org.junit.jupiter.api.AfterEach;

import java.util.Optional;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.service.PropertyService;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.repository.OwnerRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import es.module2.smapi.exceptions.PropertyAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {


    @Mock(lenient = true)
    private PropertyRepository repository;
    

    @Mock(lenient = true)
    private OwnerRepository owRepository; 


    @InjectMocks
    private PropertyService service;


    Property prop1, prop2, prop3, prop4;
    PropertyDTO propDTO1, propDTO2, propDTO3, propDTO4;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);


        propDTO1 = buildPropertyDTO(1);
        propDTO2 = buildPropertyDTO(2);
        propDTO3 = buildPropertyDTO(3);
        propDTO4 = buildPropertyDTO(4);

        //Mockito.when(addressRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(address1, address2)));
        Mockito.when(repository.findByNameAndAddress(propDTO1.getName(), propDTO1.getAddress())).thenReturn(Optional.of(prop1));
        //Mockito.when(repository.findByNameAndAddress(propDTO2.getName(), propDTO2.getAddress())).thenReurn(null);
        Mockito.when(owRepository.findByUsername(any())).thenReturn(Optional.of(new Owner("name","username")));
    }


    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception, PropertyAlreadyExistsException{
        // Property prop1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        // service.createProperty(prop1);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop1.getId());
        // repository.deleteAll();

        Mockito.when(repository.saveAndFlush(any(Property.class))).thenReturn(prop4);

        Property result = service.createProperty(propDTO4);

        assertEquals(prop4, result);
    }


    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception, PropertyAlreadyExistsException {
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
     void whenValidInputThenGetProperty() throws IOException, Exception, PropertyAlreadyExistsException {
        // Property prop4 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        // repository.save(prop4);

        // List<Property> found = repository.findAll();
        // assertThat(found).extracting(Property::getId).containsOnly(prop4.getId());


        Property found= service.getProperty(prop1.getName(), prop1.getAddress());

        assertThat(found).isNotNull();
        assertThat(found.equals(prop1));
    }
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
