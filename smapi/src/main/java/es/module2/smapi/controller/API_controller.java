package es.module2.smapi.controller;



import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class API_controller {

    @Autowired
    private final OwnerRepository repository;



    API_controller(OwnerRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/newOwner")
    Owner createOwner(@RequestBody Owner newOwner) {
        return repository.save(newOwner);
    }

    @PostMapping("/updateOwner")
    Owner updateOwner(@RequestBody Owner newOwner) {
        return repository.save(newOwner);
    }

}