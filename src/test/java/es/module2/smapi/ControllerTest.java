package es.module2.smapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Owner;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.OwnerRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
// @SpringBootTest
class ControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private OwnerRepository repository;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
     void whenValidInput_thenCreateOwner() throws IOException, Exception {
        Owner bob = new Owner( "bob@deti.com", "1234", "bob");
        mvc.perform(post("/newOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getName).containsOnly("bob");
        repository.deleteAll();
    }


    @Test
    void whenValidInput_thenUpdateOwner() throws IOException, Exception {
        Owner bob = new Owner( "bob@deti.com", "1234", "bob");
        mvc.perform(post("/newOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getName).containsOnly("bob");

        bob.setName("alex");
        mvc.perform(post("/updateOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));
        List<Owner> found2 = repository.findAll();
        assertThat(found2).extracting(Owner::getName).containsOnly("alex");
        repository.deleteAll();
    }


    @Test
    void whenValidInput_thenDeleteOwner() throws IOException, Exception {
        Owner bob = new Owner( "bob@deti.com", "1234", "bob");

        mvc.perform(post("/newOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getName).containsOnly("bob");

        mvc.perform(post("/deleteOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));
        List<Owner> found2 = repository.findAll();
        assertThat(found2 != null);
        repository.deleteAll();
    }
	
	

 }
