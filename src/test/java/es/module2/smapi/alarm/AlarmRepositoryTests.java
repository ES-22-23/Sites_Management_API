package es.module2.smapi.alarm;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.model.Property;
import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Owner;
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
class AlarmRepositoryTests {



	@Autowired
    private AlarmRepository repository;

	@Autowired
    private TestEntityManager entityManager;

    Alarm al1, al2, al3, al4;
    AlarmDTO alDTO1, alDTO2, alDTO3, alDTO4;
    Property prop1, prop2, prop3, prop4;
        
    @BeforeEach
    void setUp() throws JsonProcessingException{
        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);

        al1 = buildAlarmObject(1);
        al2 = buildAlarmObject(2);
        al3 = buildAlarmObject(3);
        al4 = buildAlarmObject(4);

        repository.saveAndFlush(al1);
        repository.saveAndFlush(al2);
        repository.saveAndFlush(al3);

        al1.setProperty(prop1);
        al2.setProperty(prop2);
        al3.setProperty(prop3);
        al4.setProperty(prop4);

        alDTO1 = buildAlarmDTO(1);
        alDTO2 = buildAlarmDTO(2);
        alDTO3 = buildAlarmDTO(3);
        alDTO4 = buildAlarmDTO(4);
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
	void whenFindAlarmByPropAndPrivateIDThenReturnProp() {

        // test the query method of interest
        Optional<Alarm> result = repository.findByPropertyAndPrivateId(prop1,alDTO1.getPrivateId());
        assertTrue(result.isPresent());
	}

    @Test
	void whenDeleteAlarmByPrivateIdThenReturnOne() {


        int result =repository.deleteByPropertyAndPrivateId(prop3,alDTO3.getPrivateId());
        assertTrue(result==1);
	}
    // @Test
	// void whenDeleteAlNotInRepThenReturnZero() {


    //     int result =repository.deleteByPrivateId(1000);
    //     assertTrue(result==0);
	// }

    Alarm buildAlarmObject(long id){
        Alarm al = new Alarm();
        al.setId(id);
        al.setPrivateId( id);
        return al;
    }

    AlarmDTO buildAlarmDTO(long id){
        AlarmDTO al = new AlarmDTO();
        al.setPrivateId( id);
        al.setPropertyAddress("Address"+id);
        al.setPropertyName("name"+id);
        return al;
    }
	
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
}
