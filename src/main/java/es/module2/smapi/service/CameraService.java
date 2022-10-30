package es.module2.smapi.service;

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
    
    
    // CRUD Func Owner

    public Camera createCamera(CameraDTO camDTO) throws CameraAlreadyExistsException, PropertyDoesNotExistException{
        log.info("Inserting Camera");

        Property p1 = propRepository.findByNameAndAddress(camDTO.getPropertyName(), camDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }
        
        Camera cam = camRepository.findByPropertyAndPrivateId(p1, camDTO.getPrivateId()).orElse(null);

        if (cam != null){
            throw new CameraAlreadyExistsException("Camera already exists: " + cam);
        }

        Camera cam2 = new Camera();
        cam2.setPrivateId(camDTO.getPrivateId());
        cam2.setProperty(p1);
        p1.getCameras().add(cam2);

        return camRepository.saveAndFlush(cam2);
    }

    public Camera getCamera(long id) {
        log.info("Getting Camera");

        return camRepository.findById(id).orElse(null);
    }

    public Camera updateCamera(CameraDTO camDTO) throws PropertyDoesNotExistException, CameraDoesNotExistException {
        log.info("Updating Camera");
        
        Property p1 = propRepository.findByNameAndAddress(camDTO.getPropertyName(), camDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }
        
        Camera cam = camRepository.findByPropertyAndPrivateId(p1, camDTO.getPrivateId()).orElse(null);

        if (cam == null){
            throw new CameraDoesNotExistException("Camera already exists: " + cam);
        }

        cam.getProperty().getCameras().remove(cam);

        cam.setPrivateId(camDTO.getPrivateId());
        cam.setProperty(p1);
        p1.getCameras().add(cam);

        return camRepository.saveAndFlush(cam);
    }

    public int deleteCamera(long id) {
        log.info("Deleting Camera");

        Optional<Camera> camera = camRepository.findById(id);

        if(camera.isEmpty()){
            return 0;
        }
        camRepository.deleteById(id);
        return 1;
    }
}