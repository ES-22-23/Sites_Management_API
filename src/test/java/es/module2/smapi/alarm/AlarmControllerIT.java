package es.module2.smapi.alarm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import org.junit.jupiter.api.AfterEach;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.AlarmRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AlarmControllerIT {

    @Autowired
    private MockMvc mvc;

	@Autowired
    private AlarmRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    /*@Test
     void whenValidInputThenCreateAlarm() throws IOException, Exception {
        Alarm al1 = new Alarm(1,new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));
        mvc.perform(post("/newAlarm").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(al1)).characterEncoding("utf-8"));

        List<Alarm> found = repository.findAll();
        assertEquals(1, found.size());
        assertEquals(1, found.get(0).getPrivateId());
        repository.deleteAll();
    }*/


    /*@Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception {
        Alarm al2 = new Alarm(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


        repository.save(al2);

        List<Alarm> found = repository.findAll();
        assertThat(found).extracting(Alarm::getId).containsOnly(al2.getId());

        al2.setId(5);
        mvc.perform(post("/updateAlarm").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(al2)));
        List<Alarm> found2 = repository.findAll();
        assertThat(found2).extracting(Alarm::getId).containsOnly(al2.getId());
        repository.deleteAll();
    }*/


    // @Test
    // void whenValidInputThenDeleteAlarm() throws IOException, Exception {
    //     Alarm al3 = new Alarm(1,new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


    //     repository.save(al3);

    //     List<Alarm> found = repository.findAll();
    //     assertThat(found).extracting(Alarm::getId).containsOnly(al3.getId());

    //     mvc.perform(post("/deleteAlarm?id="+al3.getId()));
    //     List<Alarm> found2 = repository.findAll();
    //     assertThat(found2 == null);
    //     repository.deleteAll();
    // }

    // @Test
    //  void whenValidInputThenGetAlarm() throws IOException, Exception {
    //     Alarm al3 = new Alarm(1,new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));

    //     repository.save(al3);

    //     List<Alarm> found = repository.findAll();
    //     assertThat(found).extracting(Alarm::getId).containsOnly(al3.getId());


    //     mvc.perform(get("/getAlarm?id="+al3.getId()).contentType(MediaType.APPLICATION_JSON))
    //             .andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.id", is( (int)(al3.getId()))));
    //     repository.deleteAll();
    // }
	
 }
