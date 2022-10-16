package es.module2.smapi.controller;

import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.model.Owner;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;




@RestController
class SMAPI_controller {

    @Autowired
    private OwnerRepository repository;
    
    

    @PostMapping("/newOwner")
    Owner createOwner(@RequestBody Owner newOwner) {
        return repository.save(newOwner);
    }

    @PostMapping("/updateOwner")
    Owner updateOwner(@RequestBody Owner newOwner) {
        return repository.save(newOwner);
    }
    @DeleteMapping("/deleteOwner")
    void deleteOwner(@RequestBody String username) {
        repository.deleteByUsername(username);
    }
}