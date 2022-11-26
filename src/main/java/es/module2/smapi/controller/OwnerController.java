package es.module2.smapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.datamodel.OwnerDTO;
import es.module2.smapi.exceptions.OwnerAlreadyExistsException;
import es.module2.smapi.model.Owner;
import es.module2.smapi.service.OwnerService;

@RestController
@RequestMapping("/owners")
@Validated
public class OwnerController {
    private static final Logger log = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    private OwnerService service;

    @GetMapping()
    public ResponseEntity<List<Owner>> getAllOwners(){
        log.info("GET Request -> Get all owners");

        return new ResponseEntity<>(service.getAllOwners(), HttpStatus.OK);
    }
    
    @PostMapping()
    public ResponseEntity<Owner> createOwner(@RequestBody OwnerDTO ownerDTO) {
        log.info("POST Request -> Store a new Owner");
        Owner ow = null;
        try {
            ow = service.createOwner(ownerDTO);
            return new ResponseEntity<>(ow, HttpStatus.CREATED);
        } catch (OwnerAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Owner> getOwner(@PathVariable String username) {
        log.info("GET Request -> get an Owner");
        Owner ow = service.getOwner(username);
        if (ow == null){
            return new ResponseEntity<>(ow, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ow, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Owner> updateOwner(@PathVariable String username, @RequestBody OwnerDTO ownerDTO) {
        log.info("POST Request -> Update an Owner");
        Owner ow = service.updateOwner(username, ownerDTO);
        if (ow == null){
            return new ResponseEntity<>(ow, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(ow, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Integer> deleteOwner(@PathVariable String username) {
        log.info("DELETE Request -> Delete an Owner");

        int resp = service.deleteOwner(username);
        if (resp == 1){
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

}