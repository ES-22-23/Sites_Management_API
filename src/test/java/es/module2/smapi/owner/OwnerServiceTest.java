package es.module2.smapi.camera;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Owner;
import es.module2.smapi.service.SMAPIService;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.OwnerRepository;
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


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class OwnerServiceTest {


    @Autowired
    private OwnerRepository repository;
    
    @Autowired
    private SMAPIService service;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    @Test
     void whenValidInputThenCreateOwner() throws IOException, Exception {
        Owner alex = new Owner( "alex@deti.com","1234","alex");

        service.createOwner(alex);

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenUpdateOwner() throws IOException, Exception {
        Owner alex = new Owner( "alex@deti.com","1234","alex");


        repository.save(alex);

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());

        alex.setName("bob");
        service.updateOwner(alex);
        List<Owner> found2 = repository.findAll();
        assertThat(found2).extracting(Owner::getName).containsOnly(alex.getName());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenDeleteOwner() throws IOException, Exception {
        Owner alex = new Owner( "alex@deti.com","1234","alex");


        repository.save(alex);

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());

        service.deleteOwner(alex.getUsername());
        List<Owner> found2 = repository.findAll();
        assertThat(found2 == null);
        repository.deleteAll();
    }

    @Test
     void whenValidInputThenGetOwner() throws IOException, Exception {
        Owner alex = new Owner( "alex@deti.com","1234","alex");

        repository.save(alex);

        List<Owner> found = repository.findAll();
        assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());


        Owner found2= service.getOwner(alex.getUsername());

        assertThat(found.equals(found2));
        repository.deleteAll();
    }
	
	
	

 }
