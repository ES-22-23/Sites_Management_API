package es.module2.smapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.model.Camera;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.model.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.module2.smapi.exceptions.CameraAlreadyExistsException;




@Service
public class CameraService {
    private static final Logger log = LoggerFactory.getLogger(CameraService.class);

    @Autowired
    private CameraRepository camRepository;


    @Autowired
    private PropertyRepository propRepository;
    
    
    // CRUD Func Owner

    public Camera createCamera(CameraDTO camDTO) throws CameraAlreadyExistsException{
        log.info("Inserting Camera");

        Camera cam = camRepository.findByPrivateId(camDTO.getPrivateId()).orElse(null);

        if (cam != null){
            throw new CameraAlreadyExistsException("Camera already exists: " + cam);
        }


        Camera cam2 = new Camera();
        cam2.convertDTOtoObject(camDTO);

        Property p1 = propRepository.findByNameAndAddress(camDTO.getPropertyName(), camDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            return null;
        }

        cam2.setProperty(p1);
        p1.getCameras().add(cam2);

        return camRepository.saveAndFlush(cam2);


        
    }

    public Camera getCamera(long privateId) {
        log.info("Getting Camera");

        Camera cam = camRepository.findByPrivateId(privateId).orElse(null);

        return cam;
    }

    public Camera updateCamera(CameraDTO camDTO) {
        log.info("Updating Camera");
        
        Camera  cam = camRepository.findByPrivateId(camDTO.getPrivateId()).orElse(null);


        if (cam== null){
            return null;
        }

        Camera cam2 = cam;
        Property oldProp = propRepository.findByCameras(cam2).orElse(null);
        if (oldProp!= null){
            oldProp.getCameras().remove(cam2);
        }

        cam2.convertDTOtoObject(camDTO);


        Property p1 = propRepository.findByNameAndAddress(camDTO.getPropertyName(), camDTO.getPropertyAddress()).orElse(null);

        if (p1== null){
            return null;
        }

        cam2.setProperty(p1);
        p1.getCameras().add(cam2);


        return camRepository.saveAndFlush(cam2);
    }
    
    public void deleteCamera(long privateId) {
        log.info("Deleting Camera");
        camRepository.deleteByPrivateId(privateId);
    }

}