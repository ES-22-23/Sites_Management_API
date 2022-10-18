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
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Camera;
import es.module2.smapi.service.SMAPIService;
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
class CameraServiceTest {


    @Autowired
    private CameraRepository repository;
    
    @Autowired
    private SMAPIService service;

    Gson gson = new Gson();

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
        


    @Test
     void whenValidInputThenCreateCamera() throws IOException, Exception {
        Camera cam1 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));

        service.createCamera(cam1);

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
        service.updateCamera(cam2);
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

        service.updateCamera(cam3);
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


        Camera found2= service.getCamera(cam3.getId());

        assertThat(found.equals(found2));
        repository.deleteAll();
    }
	
	
	

 }
