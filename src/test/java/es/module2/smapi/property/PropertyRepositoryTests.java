package es.module2.smapi.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.model.Property;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Alarm;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.core.JsonProcessingException;


import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.Matchers.*;

@DataJpaTest
class PropertyRepositoryTests {



	@Autowired
    private PropertyRepository propRepository;

	@Autowired
    private TestEntityManager entityManager;


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

        // cam = new Camera(1,prop1);
        // cam.setId(0);
        // prop1.getCameras().add(cam);

        // al = new Alarm(3,prop1);
        // al.setId(0);
        // prop1.getAlarms().add(al);

        propRepository.saveAndFlush(prop1);
        propRepository.saveAndFlush(prop2);
        propRepository.saveAndFlush(prop3);

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

        // test the query method of interest
        Optional<Property> result = propRepository.findByAddress(propDTO1.getAddress());
        assertTrue(result.isPresent());
	}

    @Test
	void whenFindPropByNameThenReturnProp() {

        // test the query method of interest
        Optional<Property> result = propRepository.findByName(propDTO1.getName());
        assertTrue(result.isPresent());
	}

    @Test
	void whenFindPropByNameAndAddressThenReturnProp() {

        // test the query method of interest
        Optional<Property> result = propRepository.findByNameAndAddress(propDTO1.getName(),propDTO1.getAddress());
        assertTrue(result.isPresent());
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

    // @Test
	// void whenFindPropByCameraThenReturnProp() {

    //     // test the query method of interest
    //     Optional<Property> result = propRepository.findByCameras(cam);
    //     assertTrue(result.isPresent());
	// }

    //     @Test
	// void whenFindPropByAlarmThenReturnProp() {

    //     // test the query method of interest
    //     Optional<Property> result = propRepository.findByAlarms(al);
    //     assertTrue(result.isPresent());
	// }	

    Property buildPropertyObject(long id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"name"+id);
        prop.setId(id);
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        // owRepository.saveAndFlush(ow);
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
