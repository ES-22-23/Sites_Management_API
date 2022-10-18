package es.module2.smapi.camera;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;

import org.springframework.http.MediaType;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Camera;
import es.module2.smapi.SmapiApplication;
import es.module2.smapi.repository.CameraRepository;
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
class CameraControllerIT {


    @Autowired
    private MockMvc mvc;

	@Autowired
    private CameraRepository repository;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    @Test
     void whenValidInputThenCreateCamera() throws IOException, Exception {
        Camera cam1 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));
        mvc.perform(post("/newCamera").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(cam1)).characterEncoding("utf-8"));

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam1.getId());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenUpdateCamera() throws IOException, Exception {
        Camera cam2 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


        repository.save(cam2);

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam2.getId());

        cam2.setId(5);
        mvc.perform(post("/updateCamera").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(cam2)));
        List<Camera> found2 = repository.findAll();
        assertThat(found2).extracting(Camera::getId).containsOnly(cam2.getId());
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenDeleteCamera() throws IOException, Exception {
        Camera cam3 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


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
        Camera cam3 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));

        repository.save(cam3);

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam3.getId());


         mvc.perform(get("/getCamera?id="+cam3.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is( (int)(cam3.getId()))));
        repository.deleteAll();
    }
	
	

 }
