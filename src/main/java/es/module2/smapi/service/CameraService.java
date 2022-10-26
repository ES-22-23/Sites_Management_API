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




@Service
public class CameraService {
    private static final Logger log = LoggerFactory.getLogger(CameraService.class);

    @Autowired
    private CameraRepository camRepository;


    @Autowired
    private PropertyRepository propRepository;
    
    
    // CRUD Func Owner

    public Camera createCamera(CameraDTO camDTO) {
        log.info("Inserting Camera");

        Optional<Camera> cam = camRepository.findByPrivateId(camDTO.getPrivateId());

        if (cam.isPresent()){
            // throw new AddressAlreadyExistsException("Address already exists: " + address);
            return null;
        }


        Camera cam2 = new Camera();
        cam2.convertDTOtoObject(camDTO);

        Optional<Property> p1 = propRepository.findByNameAndAddress(cam2.getProperty().getName(), cam2.getProperty().getAddress());

        if (p1.isEmpty()){
            return null;
        }

        cam2.setProperty(p1.get());
        p1.get().getCameras().add(cam2);

        return camRepository.saveAndFlush(cam2);


        
    }

    public Camera getCamera(long privateId) {
        log.info("Getting Camera");
        return camRepository.findByPrivateId(privateId);
    }

    public Camera updateCamera(CameraDTO camDTO) {
        log.info("Updating Camera");
        
        Camera cam = camRepository.findByPrivateId(camDTO.getPrivateId());

                // remove cam from old property

        Optional<Property> oldProp = propRepository.findByCameras(cam);
        if (oldProp.isPresent()){
            oldProp.get().getCameras().remove(cam);
        }

        Optional<Property> p1 = propRepository.findByNameAndAddress(cam.getProperty().getName(), cam.getProperty().getAddress());

        if (p1.isEmpty()){
            return null;
        }

        cam.setProperty(p1.get());
        p1.get().getCameras().add(cam);


        return camRepository.saveAndFlush(cam);
    }
    
    public void deleteCamera(long privateId) {
        log.info("Deleting Camera");
        camRepository.deleteByPrivateId(private_id);
    }

}