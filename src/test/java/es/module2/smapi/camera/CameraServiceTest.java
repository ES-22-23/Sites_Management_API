package es.module2.smapi.camera;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.google.gson.Gson;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.service.SMAPIService;


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
        Property prop = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        Camera cam2 = new Camera(prop);

        repository.save(cam2);

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam2.getId());
        prop.setAddress("Address2");
        cam2.setProperty(prop);
        service.updateCamera(cam2);
        List<Camera> found2 = repository.findAll();
        assertThat(found2).extracting(Camera::getProperty).containsOnly(prop);
        repository.deleteAll();
    }


    @Test
    void whenValidInputThenDeleteCamera() throws IOException, Exception {
        Camera cam3 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));


        repository.save(cam3);

        List<Camera> found = repository.findAll();
        assertThat(found).extracting(Camera::getId).containsOnly(cam3.getId());

        service.deleteCamera(cam3.getId());
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
