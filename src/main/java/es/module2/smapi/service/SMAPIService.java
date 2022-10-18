package es.module2.smapi.controller;

import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;


import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;




@RestController
class SMAPIService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propRepository;
    
    

    Owner createOwner(Owner newOwner) {
        return ownerRepository.save(newOwner);
    }

    Owner getOwner(String username) {
        return ownerRepository.findByUsername(username);
    }

    Owner updateOwner(Owner newOwner) {
        return ownerRepository.save(newOwner);
    }
    void deleteOwner(String username) {
        ownerRepository.deleteByUsername(username);
    }


    Property createProperty(Property newProperty) {
        return propRepository.save(newProperty);
    }

    Property getProperty(String address) {
        return propRepository.findByAddress(address);
    }

    Property updateProperty(Property newProperty) {
        return propRepository.save(newProperty);
    }
    void deleteProperty(long id) {
        propRepository.deleteById(id);
    }



}