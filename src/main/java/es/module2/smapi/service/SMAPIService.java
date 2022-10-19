package es.module2.smapi.service;

import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Camera;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;




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

    public Camera createCamera(Camera newCamera) {
        return camRepository.save(newCamera);
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

    public Alarm createAlarm(Alarm newAlarm) {
        return alRepository.save(newAlarm);
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