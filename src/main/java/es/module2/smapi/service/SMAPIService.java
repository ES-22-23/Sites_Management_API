package es.module2.smapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;




@Service
public class SMAPIService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propRepository;

    @Autowired
    private CameraRepository camRepository;


    @Autowired
    private AlarmRepository alRepository;
    
    
    // CRUD Func Owner

    public Owner createOwner(Owner newOwner) {
        return ownerRepository.save(newOwner);
    }

    public Owner getOwner(String username) {
        return ownerRepository.findByUsername(username);
    }

    public Owner updateOwner(Owner newOwner) {
        return ownerRepository.save(newOwner);
    }

    public void deleteOwner(String username) {
        ownerRepository.deleteByUsername(username);
    }


    // CRUD Func Properties

    public Property createProperty(Property newProperty) {
        return propRepository.save(newProperty);
    }

    public Property getProperty(String address) {
        return propRepository.findByAddress(address);
    }

    public Property updateProperty(Property newProperty) {
        return propRepository.save(newProperty);
    }
    public void deleteProperty(long id) {
        propRepository.deleteById(id);
    }


    // CRUD Func Camera

    public Camera createCamera(CameraDTO cameraDTO) {

        Camera newCamera = new Camera();

        newCamera = camRepository.saveAndFlush(newCamera);

        Optional<Property> p1 = propRepository.findByNameAndAddress(cameraDTO.getPropertyName(), cameraDTO.getPropertyAddress());

        if (p1.isEmpty()){
            return null;
        }
        newCamera.setProperty(p1.get());
        p1.get().getCameras().add(newCamera);

        return newCamera;
    }

    public Camera getCamera(long id) {
        return camRepository.findById(id);
    }

    public Camera updateCamera(Camera newCamera) {
        return camRepository.save(newCamera);
    }
    public void deleteCamera(long id) {
        camRepository.deleteById(id);
    }



    // CRUD Func Alarm

    public Alarm createAlarm(AlarmDTO alarmDTO) {
        Alarm newAlarm = new Alarm();

        newAlarm = alRepository.saveAndFlush(newAlarm);

        Optional<Property> p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress());

        if (p1.isEmpty()){
            return null;
        }
        newAlarm.setProperty(p1.get());
        p1.get().getAlarms().add(newAlarm);

        return newAlarm;
    }

    public Alarm getAlarm(long id) {
        return alRepository.findById(id);
    }

    public Alarm updateAlarm(Alarm newAlarm) {
        return alRepository.save(newAlarm);
    }
    public void deleteAlarm(long id) {
        alRepository.deleteById(id);
    }


}