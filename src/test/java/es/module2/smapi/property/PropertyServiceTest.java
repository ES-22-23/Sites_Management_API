package es.module2.smapi.property;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.exceptions.PropertyAlreadyExistsException;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.service.PropertyService;

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

        Mockito.when(repository.findByNameAndAddress(propDTO1.getName(), propDTO1.getAddress())).thenReturn(Optional.of(prop1));
        Mockito.when(owRepository.findByUsername(any())).thenReturn(Optional.of(new Owner("username","email", "name")));
    }


    @Test
     void whenValidInputThenCreateProperty() throws IOException, Exception, PropertyAlreadyExistsException{

        Mockito.when(repository.saveAndFlush(any(Property.class))).thenReturn(prop4);

        Property result = service.createProperty(propDTO4);

        assertEquals(prop4, result);
    }

    @Test
    void whenValidInputThenUpdateProperty() throws IOException, Exception, PropertyAlreadyExistsException {

        Mockito.when(repository.saveAndFlush(any(Property.class))).thenReturn(prop1);
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(prop1));

        Property result = service.updateProperty(1,propDTO1);

        assertTrue(prop1.equals(result));
    }

    @Test
     void whenValidInputThenGetProperty() throws IOException, Exception, PropertyAlreadyExistsException {
   
        Mockito.when(repository.findById((long)1)).thenReturn(Optional.of(prop1));

        Property found= service.getProperty(1);

        assertThat(found).isNotNull();
        assertThat(found.equals(prop1));
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
