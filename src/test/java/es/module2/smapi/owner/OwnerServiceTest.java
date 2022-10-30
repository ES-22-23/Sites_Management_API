package es.module2.smapi.owner;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.OwnerRepository;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class OwnerServiceTest {


    @Autowired
    private OwnerRepository repository;
    

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    // @Test
    //  void whenValidInputThenCreateOwner() throws IOException, Exception {
    //     Owner alex = new Owner( "alex@deti.com","1234","alex");

    //     service.createOwner(alex);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());
    //     repository.deleteAll();
    // }


    // @Test
    // void whenValidInputThenUpdateOwner() throws IOException, Exception {
    //     Owner alex = new Owner( "alex@deti.com","1234","alex");


    //     repository.save(alex);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());

    //     alex.setName("bob");
    //     service.updateOwner(alex);
    //     List<Owner> found2 = repository.findAll();
    //     assertThat(found2).extracting(Owner::getName).containsOnly(alex.getName());
    //     repository.deleteAll();
    // }


    // @Test
    // void whenValidInputThenDeleteOwner() throws IOException, Exception {
    //     Owner alex = new Owner( "alex@deti.com","1234","alex");


    //     repository.save(alex);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());

    //     service.deleteOwner(alex.getUsername());
    //     List<Owner> found2 = repository.findAll();
    //     assertThat(found2 == null);
    //     repository.deleteAll();
    // }

    // @Test
    //  void whenValidInputThenGetOwner() throws IOException, Exception {
    //     Owner alex = new Owner( "alex@deti.com","1234","alex");

    //     repository.save(alex);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getUsername).containsOnly(alex.getUsername());


    //     Owner found2= service.getOwner(alex.getUsername());

    //     assertThat(found.equals(found2));
    //     repository.deleteAll();
    // }
	
	
	

 }
