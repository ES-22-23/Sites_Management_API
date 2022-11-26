package es.module2.smapi.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.PropertyRepository;

@DataJpaTest
class PropertyRepositoryTests {



	@Autowired
    private PropertyRepository propRepository;

    Property prop1, prop2, prop3, prop4;
    PropertyDTO propDTO1, propDTO2, propDTO3, propDTO4;
    Camera cam;
    Alarm al;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);

        prop1 = propRepository.saveAndFlush(prop1);
        prop2 = propRepository.saveAndFlush(prop2);
        prop3 = propRepository.saveAndFlush(prop3);

        propDTO1 = buildPropertyDTO(1);
        propDTO2 = buildPropertyDTO(2);
        propDTO3 = buildPropertyDTO(3);
        propDTO4 = buildPropertyDTO(4);
    }

    @AfterEach
    void cleanUp(){
        propRepository.deleteAll();
    }

	@Test
	void whenFindPropByAddressThenReturnProp() {

        Optional<Property> result = propRepository.findByAddress(propDTO1.getAddress());
        assertTrue(result.isPresent());
	}

    @Test
	void whenFindPropByNameThenReturnProp() {

        Optional<Property> result = propRepository.findByName(propDTO1.getName());
        assertTrue(result.isPresent());
	}

    @Test
	void whenFindPropByNameAndAddressThenReturnProp() {

        Optional<Property> result = propRepository.findByNameAndAddress(propDTO1.getName(),propDTO1.getAddress());
        assertTrue(result.isPresent());
        assertEquals(prop1, result.get());
	}


    @Test
	void whenDeletePropInRepositoryThenPropNoLongerInRepository() {

        int result = propRepository.deleteByNameAndAddress(propDTO3.getName(),propDTO3.getAddress());
        assertTrue(result==1);
	}

    @Test
	void whenDeletePropNotInRepThenReturnZero() {

        int result = propRepository.deleteByNameAndAddress("randomNAme","randomAddress");
        assertTrue(result==0);
	}

    Property buildPropertyObject(long id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"email"+id,"name"+id);
        prop.setId(id);
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        return prop;
    }

    PropertyDTO buildPropertyDTO(long id){
        PropertyDTO prop = new PropertyDTO();
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwnerUsername("username"+id);
        prop.setId(id);
        return prop;
    }
}
