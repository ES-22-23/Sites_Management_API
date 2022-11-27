package es.module2.smapi.alarm;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;

@DataJpaTest
class AlarmRepositoryTests {

	@Autowired
    private AlarmRepository repository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    Alarm al1, al2, al3, al4;
    AlarmDTO alDTO1, alDTO2, alDTO3, alDTO4;
    Property prop1, prop2, prop3, prop4;
        
    @BeforeEach
    void setUp() throws JsonProcessingException{
        prop1 = buildPropertyObject("1");
        prop2 = buildPropertyObject("2");
        prop3 = buildPropertyObject("3");
        prop4 = buildPropertyObject("4");

        al1 = buildAlarmObject("1");
        al2 = buildAlarmObject("2");
        al3 = buildAlarmObject("3");
        al4 = buildAlarmObject("4");

        al1.setProperty(prop1);
        al2.setProperty(prop2);
        al3.setProperty(prop3);
        al4.setProperty(prop4);

        repository.saveAndFlush(al1);
        repository.saveAndFlush(al2);
        repository.saveAndFlush(al3);

        alDTO1 = buildAlarmDTO("1");
        alDTO2 = buildAlarmDTO("2");
        alDTO3 = buildAlarmDTO("3");
        alDTO4 = buildAlarmDTO("4");
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
	void whenFindAlarmByPropAndIDThenReturnProp() {

        // test the query method of interest
        Optional<Alarm> result = repository.findByPropertyAndId(prop1,alDTO1.getId());
        assertTrue(result.isPresent());
	}

    Alarm buildAlarmObject(String id){
        Alarm al = new Alarm();
        al.setId(id);
        al.setId( id);
        return al;
    }

    AlarmDTO buildAlarmDTO(String id){
        AlarmDTO al = new AlarmDTO();
        al.setId( id);
        al.setPropertyAddress("Address"+id);
        al.setPropertyName("name"+id);
        return al;
    }
	
    Property buildPropertyObject(String id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"email"+id,"name"+id);
        ow = ownerRepository.saveAndFlush(ow);
        prop.setId(id);
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        prop = propertyRepository.saveAndFlush(prop);
        ow.getProperties().add(prop);
        return prop;
    }
}
