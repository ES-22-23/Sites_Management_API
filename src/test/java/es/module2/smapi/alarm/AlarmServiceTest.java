package es.module2.smapi.alarm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import org.junit.jupiter.api.AfterEach;

import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.AlarmRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AlarmServiceTest {


    @Autowired
    private AlarmRepository repository;
    

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    /*@Test
     void whenValidInputThenCreateAlarm() throws IOException, Exception {
        Alarm dal1 = new Alarm(1, new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));

        service.createAlarm(dal1);

        List<Alarm> found = repository.findAll();
        assertThat(found).extracting(Alarm::getId).containsOnly(dal1.getId());
        repository.deleteAll();
    }*/


    @Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception {
        Property prop = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        Alarm dal2 = new Alarm(1, prop);

        repository.save(dal2);

        List<Alarm> found = repository.findAll();
        assertThat(found).extracting(Alarm::getId).containsOnly(dal2.getId());
        prop.setAddress("Address2");
        dal2.setProperty(prop);
        service.updateAlarm(dal2);
        List<Alarm> found2 = repository.findAll();
        assertThat(found2).extracting(Alarm::getProperty).containsOnly(prop);
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenDeleteAlarm() throws IOException, Exception {
        Alarm dal3 = new Alarm(1, new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


        repository.save(dal3);

        List<Alarm> found = repository.findAll();
        assertThat(found).extracting(Alarm::getId).containsOnly(dal3.getId());

        service.updateAlarm(dal3);
        List<Alarm> found2 = repository.findAll();
        assertThat(found2 == null);
        repository.deleteAll();
    }

    @Test
     void whenValidInputThenGetAlarm() throws IOException, Exception {
        Alarm dal3 = new Alarm(1, new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));

        repository.save(dal3);

        List<Alarm> found = repository.findAll();
        assertThat(found).extracting(Alarm::getId).containsOnly(dal3.getId());


        Alarm found2= service.getAlarm(dal3.getId());

        assertThat(found.equals(found2));
        repository.deleteAll();
    }
	
	
	

 }
