package es.module2.smapi.camera;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;

@DataJpaTest
class CameraRepositoryTests {

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
        prop1 = buildPropertyObject("1");
        prop2 = buildPropertyObject("2");
        prop3 = buildPropertyObject("3");
        prop4 = buildPropertyObject("4");

        cam1 = buildCameraObject("1");
        cam2 = buildCameraObject("2");
        cam3 = buildCameraObject("3");
        cam4 = buildCameraObject("4");

        cam1.setProperty(prop1);
        cam2.setProperty(prop2);
        cam3.setProperty(prop3);
        cam4.setProperty(prop4);

        repository.saveAndFlush(cam1);
        repository.saveAndFlush(cam2);
        repository.saveAndFlush(cam3);

        camDTO1 = buildCameraDTO("1");
        camDTO2 = buildCameraDTO("2");
        camDTO3 = buildCameraDTO("3");
        camDTO4 = buildCameraDTO("4");
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
	void whenFindCameraByPropAndIdThenReturnProp() {

        Optional<Camera> result = repository.findByPropertyAndId(prop1,camDTO1.getId());
        assertTrue(result.isPresent());
	}

    Camera buildCameraObject(String id){
        Camera al = new Camera();
        al.setId( id);
        return al;
    }

    CameraDTO buildCameraDTO(String id){
        CameraDTO al = new CameraDTO();
        al.setId(id);
        al.setPropertyAddress("Address"+id);
        al.setPropertyName("name"+id);
        return al;
    }
	
    Property buildPropertyObject(String id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id,"email"+id,"name"+id);
        ow = ownerRepository.saveAndFlush(ow);
        prop.setId(Long.parseLong(id));
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        prop = propertyRepository.saveAndFlush(prop);
        ow.getProperties().add(prop);
        return prop;
    }
}