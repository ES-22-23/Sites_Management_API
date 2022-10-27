package es.module2.smapi.alarm;


import es.module2.smapi.model.Property;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.service.AlarmService;
import org.junit.jupiter.api.Test;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.IOException;

import java.util.Optional;
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
import es.module2.smapi.exceptions.AlarmAlreadyExistsException;


@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {


    @Mock(lenient = true)
    private PropertyRepository propRepository;
    

    @Mock(lenient = true)
    private AlarmRepository repository; 


    @InjectMocks
    private AlarmService service;
    

    Alarm al1, al2, al3, al4;
    AlarmDTO alDTO1, alDTO2, alDTO3, alDTO4;
        
    @BeforeEach
    void setUp() throws JsonProcessingException{

        al1 = buildAlarmObject(1);
        al2 = buildAlarmObject(2);
        al3 = buildAlarmObject(3);
        al4 = buildAlarmObject(4);


        alDTO1 = buildAlarmDTO(1);
        alDTO2 = buildAlarmDTO(2);
        alDTO3 = buildAlarmDTO(3);
        alDTO4 = buildAlarmDTO(4);

        Mockito.when(repository.findByPrivateId(alDTO1.getPrivateId())).thenReturn(Optional.of(al1));
        Mockito.when(propRepository.findByNameAndAddress(any(),any())).thenReturn(Optional.of(new Property("address","name",new Owner("username","name"))));
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }
        
    @Test
     void whenValidInputThenCreateAlarm() throws IOException, Exception, AlarmAlreadyExistsException{

        Mockito.when(repository.saveAndFlush(any(Alarm.class))).thenReturn(al4);

        Alarm result = service.createAlarm(alDTO4);

        assertEquals(al4, result);
    }
    @Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception, AlarmAlreadyExistsException {

        Mockito.when(repository.saveAndFlush(any(Alarm.class))).thenReturn(al1);

        Alarm result = service.updateAlarm(alDTO1);

        assertTrue(al1.equals(result));
    }

    @Test
     void whenValidInputThenGetAlarm() throws IOException, Exception, AlarmAlreadyExistsException {


        Alarm found= service.getAlarm(al1.getPrivateId());

        assertThat(found).isNotNull();
        assertTrue(found.equals(al1));
    }

    @Test
     void whenValidInputThenDeleteAlarm() throws IOException, Exception, AlarmAlreadyExistsException {


        int found= service.deleteAlarm(al1.getPrivateId());

        assertTrue(found==1);
    }


    Alarm buildAlarmObject(long id){
        Alarm al = new Alarm();
        Property prop= new Property("address"+id,"name"+id,new Owner("username","name"));
        al.setId(id);
        al.setPrivateId( id);
        al.setProperty(prop);
        propRepository.saveAndFlush(prop);
        return al;
    }

    AlarmDTO buildAlarmDTO(long id){
        AlarmDTO al = new AlarmDTO();
        al.setPrivateId( id);
        al.setPropertyAddress("Address"+id);
        al.setPropertyName("name"+id);
        return al;
    }
	
	

 }
