package es.module2.smapi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.exceptions.CameraAlreadyExistsException;
import es.module2.smapi.exceptions.CameraDoesNotExistException;
import es.module2.smapi.exceptions.PropertyDoesNotExistException;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.PropertyRepository;




@Service
public class CameraService {
    private static final Logger log = LoggerFactory.getLogger(CameraService.class);

    @Autowired
    private CameraRepository camRepository;


    @Autowired
    private PropertyRepository propRepository;
    
    public List<Camera> getAllCameras(){
        log.info("Getting All Cameras");

        return camRepository.findAll();
    }

    public Camera createCamera(CameraDTO camDTO) throws CameraAlreadyExistsException, PropertyDoesNotExistException{
        log.info("Inserting Camera");

        Property p1 = propRepository.findByNameAndAddress(camDTO.getPropertyName(), camDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }
        
        Camera cam = camRepository.findByPropertyAndId(p1, camDTO.getId()).orElse(null);

        if (cam != null){
            throw new CameraAlreadyExistsException("Camera already exists: " + cam);
        }

        Camera cam2 = new Camera();
        cam2.setId(camDTO.getId());
        cam2.setProperty(p1);
        p1.getCameras().add(cam2);

        return camRepository.saveAndFlush(cam2);
    }

    public Camera getCamera(String id) {
        log.info("Getting Camera");

        return camRepository.findById(id).orElse(null);
    }

    public Camera updateCamera(String id, CameraDTO camDTO) throws PropertyDoesNotExistException, CameraDoesNotExistException {
        log.info("Updating Camera");
        
        Property p1 = propRepository.findByNameAndAddress(camDTO.getPropertyName(), camDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }
        
        Camera cam = camRepository.findById(id).orElse(null);

        if (cam == null){
            throw new CameraDoesNotExistException("Camera already exists: " + cam);
        }

        cam.getProperty().getCameras().remove(cam);

        cam.setId(camDTO.getId());
        cam.setProperty(p1);
        p1.getCameras().add(cam);

        return camRepository.saveAndFlush(cam);
    }

    public int deleteCamera(String id) {
        log.info("Deleting Camera");

        Optional<Camera> cameraOptional = camRepository.findById(id);

        if(cameraOptional.isEmpty()){
            return 0;
        }

        Camera camera = cameraOptional.get();
        Property property = propRepository.findById(camera.getProperty().getId()).get();

        property.getCameras().remove(camera);
        camera.setProperty(null);

        propRepository.save(property);
        camRepository.saveAndFlush(camera);

        camRepository.deleteById(id);
        return  1;
    }
}