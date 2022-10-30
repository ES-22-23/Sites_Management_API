package es.module2.smapi.camera;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CameraControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

	@Autowired
    private CameraRepository repository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    Camera cam1, cam2, cam3, cam4;
    CameraDTO camDTO1, camDTO2, camDTO3, camDTO4;
    Property prop1, prop2, prop3, prop4;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        prop1 = buildPropertyObject(1);
        prop2 = buildPropertyObject(2);
        prop3 = buildPropertyObject(3);
        prop4 = buildPropertyObject(4);

        cam1 = buildCameraObject(1);
        cam2 = buildCameraObject(2);
        cam3 = buildCameraObject(3);

        cam1.setProperty(prop1);
        cam2.setProperty(prop2);
        cam3.setProperty(prop3);

        cam1 = repository.saveAndFlush(cam1);
        cam2 = repository.saveAndFlush(cam2);
        cam3 = repository.saveAndFlush(cam3);

        camDTO1 = buildCameraDTO(1);
        camDTO2 = buildCameraDTO(2);
        camDTO3 = buildCameraDTO(3);
        camDTO4 = buildCameraDTO(4);
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
        propertyRepository.deleteAll();
    }


    @Test
     void testGetAllCameras() throws IOException, Exception {

        given().get("/cameras")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0].id", is((int)cam1.getId())).and()
        .body("[1].id", is((int)cam2.getId())).and()
        .body("[2].id", is((int)cam3.getId()));

    }

    @Test
     void whenValidInputThenCreateCamera() throws IOException, Exception {
        given().contentType(ContentType.JSON).body(camDTO4)
        .post("/cameras")
        .then().log().body().assertThat()
        .status(HttpStatus.CREATED).and()
        .contentType(ContentType.JSON).and()
        .body("privateId", is((int) camDTO4.getPrivateId()));
        
    }
    @Test
     void whenInvalidInputThenNotCreateCamera() throws IOException, Exception {
        given().contentType(ContentType.JSON).body(camDTO1)
        .post("/cameras")
        .then().log().body().assertThat()
        .status(HttpStatus.BAD_REQUEST);
        
    }

    @Test
    void whenValidInputThenDeleteCamera() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .delete("/cameras/"+cam2.getId())
        .then().log().body().assertThat()
        .status(HttpStatus.OK);

    }

    @Test
    void whenInvalidInputThenNotFound() throws IOException, Exception {

        given().contentType(ContentType.JSON)
        .get("/cameras/"+1000)
        .then().log().body().assertThat()
        .status(HttpStatus.NOT_FOUND);

    }

    @Test
     void whenValidInputThenGetCamera() throws IOException, Exception {

        given().get("/cameras/"+cam1.getId())
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("id", is((int)cam1.getId()));

    }

    Camera buildCameraObject(long id){
        Camera cam = new Camera();
        cam.setId(id);
        cam.setPrivateId( id);
        return cam;
    }

    CameraDTO buildCameraDTO(long id){
        CameraDTO cam = new CameraDTO();
        cam.setPrivateId( id);
        cam.setPropertyAddress("address"+id);
        cam.setPropertyName("name"+id);
        return cam;
    }
	
    Property buildPropertyObject(long id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"email" + id, "name"+id);
        ow = ownerRepository.saveAndFlush(ow);
        prop.setId(id);
        prop.setName("name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        prop = propertyRepository.saveAndFlush(prop);
        ow.getProperties().add(prop);
        return prop;
    }
}
