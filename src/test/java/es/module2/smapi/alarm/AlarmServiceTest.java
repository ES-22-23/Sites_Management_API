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
import static org.mockito.ArgumentMatchers.eq;
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
    Property prop1, prop2, prop3, prop4;
    AlarmDTO alDTO1, alDTO2, alDTO3, alDTO4;
    Property prop;
        
    @BeforeEach
    void setUp() throws JsonProcessingException{

        al1 = buildAlarmObject(1);
        al2 = buildAlarmObject(2);
        al3 = buildAlarmObject(3);
        al4 = buildAlarmObject(4);

        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);
        
        al1.setProperty(prop1);
        al2.setProperty(prop2);
        al3.setProperty(prop3);
        al4.setProperty(prop4);

        alDTO1 = buildAlarmDTO(1);
        alDTO2 = buildAlarmDTO(2);
        alDTO3 = buildAlarmDTO(3);
        alDTO4 = buildAlarmDTO(4);

        Mockito.when(repository.findByPropertyAndPrivateId(any(), eq(alDTO1.getPrivateId()))).thenReturn(Optional.of(al1));
    }

        
    @Test
     void whenValidInputThenCreateAlarm() throws IOException, Exception, AlarmAlreadyExistsException{
        
        Mockito.when(propRepository.findByNameAndAddress(any(),any())).thenReturn(Optional.of(prop4));
        Mockito.when(repository.saveAndFlush(any(Alarm.class))).thenReturn(al4);

        Alarm result = service.createAlarm(alDTO4);

        assertEquals(al4, result);
    }
    @Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception, AlarmAlreadyExistsException {

        Mockito.when(propRepository.findByNameAndAddress(any(),any())).thenReturn(Optional.of(prop1));
        Mockito.when(repository.saveAndFlush(any(Alarm.class))).thenReturn(al1);

        Alarm result = service.updateAlarm(alDTO1);
        System.out.println(result);
        assertTrue(al1.equals(result));
    }

    @Test
     void whenValidInputThenGetAlarm() throws IOException, Exception, AlarmAlreadyExistsException {

        Mockito.when(propRepository.findByNameAndAddress(prop1.getName(),prop1.getAddress())).thenReturn(Optional.of(prop1));
        Mockito.when(repository.findById(al1.getId())).thenReturn(Optional.of(al1));
        Alarm found= service.getAlarm(al1.getId());

        assertThat(found).isNotNull();
        assertTrue(found.equals(al1));
    }



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
        propRepository.saveAndFlush(prop);
        return prop;
    }
	
	

 }
