package es.module2.smapi.camera;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.PropertyRepository;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CameraControllerIT {
 
    @Autowired
    private MockMvc mvc;

	@Autowired
    private CameraRepository repository;

    @Autowired
    private PropertyRepository propertyRepository;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


   /*@Test
     void whenValidInputThenCreateCamera() throws IOException, Exception {
        Property p1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));

        p1= propertyRepository.save(p1);

        Camera cam1 = new Camera();
        cam1.setPrivateId(1);
        cam1.setProperty(p1);

        mvc.perform(post("/newCamera").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(cam1)).characterEncoding("utf-8"));

        assertEquals(1, repository.findAll().size());
        repository.deleteAll();
    }*/


    /*@Test
    void whenValidInputThenUpdateCamera() throws IOException, Exception {
        Property p1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        Camera cam2 = new Camera(p1);

        cam2 = repository.save(cam2);

        assertEquals(p1, repository.findById(cam2.getId()).getProperty());

        Property p2 = new Property( "address2","DETI",new Owner( "alex@deti.com","1234","alex"));
        Camera cam3 = new Camera(p2);

        mvc.perform(post("/updateCamera").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(cam3)));

        assertEquals(p2, repository.findById(cam2.getId()).getProperty());
        repository.deleteAll();
    }*/


    /*@Test
    void whenValidInputThenDeleteCamera() throws IOException, Exception {
        Camera cam3 = new Camera(1, new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


        repository.save(cam3);

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam3.getId());

        mvc.perform(post("/deleteCamera?id="+cam3.getId()));
        List<Camera> found2 = repository.findAll();
        assertThat(found2 == null);
        repository.deleteAll();
    }

    @Test
     void whenValidInputThenGetCamera() throws IOException, Exception {
        Camera cam3 = new Camera(1, new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));

        repository.save(cam3);

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam3.getId());


         mvc.perform(get("/getCamera?id="+cam3.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is( (int)(cam3.getId()))));
        repository.deleteAll();
    }*/

 }
