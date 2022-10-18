package es.module2.smapi.controller;

import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.model.Owner;


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

    Owner updateOwner(Owner newOwner) {
        return ownerRepository.save(newOwner);
    }
    void deleteOwner(String username) {
        ownerRepository.deleteByUsername(username);
    }



}