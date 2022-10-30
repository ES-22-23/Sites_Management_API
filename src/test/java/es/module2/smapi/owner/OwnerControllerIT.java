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
// @SpringBootTest
class OwnerControllerIT {

    // @Autowired
    // private MockMvc mvc;

    @Autowired
    private OwnerRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    // @Test
    //  void whenValidInputThenCreateOwner() throws IOException, Exception {
    //     Owner bob = new Owner( "bob@deti.com", "1234", "bob");
    //     mvc.perform(post("/newOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getName).containsOnly("bob");
    //     repository.deleteAll();
    // }


    // @Test
    // void whenValidInputThenUpdateOwner() throws IOException, Exception {
    //     Owner bob = new Owner( "bob@deti.com", "1234", "bob");
    //     repository.save(bob);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getName).containsOnly("bob");

    //     bob.setName("alex");
    //     mvc.perform(post("/updateOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));
    //     List<Owner> found2 = repository.findAll();
    //     assertThat(found2).extracting(Owner::getName).containsOnly("alex");
    //     repository.deleteAll();
    // }


    // @Test
    // void whenValidInputThenDeleteOwner() throws IOException, Exception {
    //     Owner bob = new Owner( "bob@deti.com", "1234", "bob");

    //     repository.save(bob);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getName).containsOnly("bob");

    //     mvc.perform(post("/deleteOwner").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bob)));
    //     List<Owner> found2 = repository.findAll();
    //     assertThat(found2 == null);
    //     repository.deleteAll();
    // }

    // @Test
    //  void whenFindAlexByUsernameThenReturnAlexOwner() throws IOException, Exception {
    //     Owner alex = new Owner( "alex@deti.com", "1234", "alex");
    //     repository.save(alex);

    //     List<Owner> found = repository.findAll();
    //     assertThat(found).extracting(Owner::getName).containsOnly("alex");


    //      mvc.perform(get("/getOwner?username={username}",alex.getUsername()).contentType(MediaType.APPLICATION_JSON))
    //             .andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.name", is("alex")));
    //     repository.deleteAll();
    // }
 }
