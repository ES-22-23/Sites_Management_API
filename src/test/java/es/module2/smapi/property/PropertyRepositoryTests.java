package es.module2.smapi.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.model.Property;
import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Alarm;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


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

        RestAssuredMockMvc.mockMvc( mvc );

        prop1 = buildAddressObject(1);
        prop2 = buildAddressObject(2);
        prop3 = buildAddressObject(3);
        prop4 = buildAddressObject(4);

        cam = new Camera(1,prop1);
        prop1.getCameras().add(prop1);

        al = new Alarm(3,prop1);
        prop1.getCameras().add(prop1);

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
        entityManager.clear();
    }



	@Test
	void whenFindPropByAddressThenReturnProp() {

        // test the query method of interest
        Property result = propRepository.findByAddress(propDTO1.getAddress());
        assertThat( result ).isEqualTo(propDTO1);
	}

    @Test
	void whenFindPropByNameThenReturnProp() {

        // test the query method of interest
        Property result = propRepository.findByName(propDTO1.getName());
        assertThat( result ).isEqualTo(propDTO1);
	}

    @Test
	void whenFindPropByNameAndAddressThenReturnProp() {

        // test the query method of interest
        Optional<Property> result = propRepository.findByNameAndAddress(propDTO1.getName(),propDTO1.getAddress());
        assertTrue(result.isPresent());
	}


    @Test
	void whenDeletePropInRepositoryThenPropNoLongerInRepository() {
        // Property prop3 = new Property( "address3","Bio",new Owner( "alex@deti.com","1234","alex"));
        // entityManager.persistAndFlush(prop3); //ensure data is persisted at this point

        // // test the query method of interest
        // Property found = propRepository.findByName(prop3.getName());
        // assertThat( found ).isEqualTo(prop3);

        // propRepository.deleteById(prop3.getId());
        // List<Property> found2 = propRepository.findAll();
        // assertThat(found2.isEmpty()).isTrue();

        Property result = propRepository.deleteByNameAndAddress(propDTO3.getName(),propDTO3.getAddress());
        assertThat(result).isNull();
	}

    @Test
	void whenFindPropByCameraThenReturnProp() {

        // test the query method of interest
        Optional<Property> result = propRepository.findByCameras(cam);
        assertTrue(result.isPresent());
	}

        @Test
	void whenFindPropByAlarmThenReturnProp() {

        // test the query method of interest
        Optional<Property> result = propRepository.findByAlarms(al);
        assertTrue(result.isPresent());
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
