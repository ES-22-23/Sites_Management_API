package es.module2.smapi.camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.service.CameraService;

@ExtendWith(MockitoExtension.class)
public class CameraServiceTest {
    
    @Mock(lenient = true)
    private PropertyRepository propRepository;
    

    @Mock(lenient = true)
    private CameraRepository repository; 


    @InjectMocks
    private CameraService service;
    

    Camera cam1, cam2, cam3, cam4;
    Property prop1, prop2, prop3, prop4;
    CameraDTO camDTO1, camDTO2, camDTO3, camDTO4;
        
    @BeforeEach
    void setUp() throws JsonProcessingException{

        cam1 = buildCameraObject("1");
        cam2 = buildCameraObject("2");
        cam3 = buildCameraObject("3");
        cam4 = buildCameraObject("4");

        prop1 = buildPropertyObject("1");
        prop2 = buildPropertyObject("2");
        prop3 = buildPropertyObject("3");
        prop4 = buildPropertyObject("4");
        
        cam1.setProperty(prop1);
        cam2.setProperty(prop2);
        cam3.setProperty(prop3);
        cam4.setProperty(prop4);

        camDTO1 = buildCameraDTO("1");
        camDTO2 = buildCameraDTO("2");
        camDTO3 = buildCameraDTO("3");
        camDTO4 = buildCameraDTO("4");

        Mockito.when(repository.findByPropertyAndId(any(), eq(camDTO1.getId()))).thenReturn(Optional.of(cam1));
    }

    @Test
     void whenValidInputThenCreateCamera() throws IOException, Exception{
        
        Mockito.when(propRepository.findByNameAndAddress(any(),any())).thenReturn(Optional.of(prop4));
        Mockito.when(repository.saveAndFlush(any(Camera.class))).thenReturn(cam4);

        Camera result = service.createCamera(camDTO4);

        assertEquals(cam4, result);
    }
    @Test
    void whenValidInputThenUpdateAlarm() throws IOException, Exception {

        Mockito.when(propRepository.findByNameAndAddress(any(),any())).thenReturn(Optional.of(prop1));
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(cam1));
        Mockito.when(repository.saveAndFlush(any(Camera.class))).thenReturn(cam1);

        Camera result = service.updateCamera("1", camDTO1);
        assertTrue(cam1.equals(result));
    }

    @Test
     void whenValidInputThenGetAlarm() throws IOException, Exception {

        Mockito.when(propRepository.findByNameAndAddress(prop1.getName(),prop1.getAddress())).thenReturn(Optional.of(prop1));
        Mockito.when(repository.findById(cam1.getId())).thenReturn(Optional.of(cam1));
        Camera found= service.getCamera(cam1.getId());

        assertNotNull(found);
        assertEquals(cam1,found);
    }

    Camera buildCameraObject(String id){
        Camera cam = new Camera();
        cam.setId(id);
        cam.setId( id);
        return cam;
    }

    CameraDTO buildCameraDTO(String id){
        CameraDTO cam = new CameraDTO();
        cam.setId(id);
        cam.setPropertyAddress("Address"+id);
        cam.setPropertyName("name"+id);
        return cam;
    }

    Property buildPropertyObject(String id){
        Property prop = new Property();
        Owner ow= new Owner("username"+id, "email"+id, "name"+id);
        prop.setId(Long.parseLong(id));
        prop.setName("Name" + id);
        prop.setAddress("address"  + id);
        prop.setOwner(ow);
        propRepository.saveAndFlush(prop);
        return prop;
    }
}
