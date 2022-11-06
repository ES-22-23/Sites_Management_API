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

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.exceptions.OwnerDoesNotExistException;
import es.module2.smapi.exceptions.PropertyAlreadyExistsException;
import es.module2.smapi.model.Property;
import es.module2.smapi.service.PropertyService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/properties")
@Validated
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@CrossOrigin(origins = "http://panel.admin.hgsoft.me:3000/", allowedHeaders = "*")
class PropertyController {
    private static final Logger log = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService service;

    @GetMapping()
    public ResponseEntity<List<Property>> getAllProperties(){
        log.info("GET Request -> Get all properties");

        return new ResponseEntity<>(service.getAllProperties(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Property> createProperty(@RequestBody PropertyDTO propertyDTO) {
        log.info("POST Request -> Store a new Property");
        Property prop = null;
        try {
            prop = service.createProperty(propertyDTO);
            return new ResponseEntity<>(prop, HttpStatus.CREATED);
        } catch (PropertyAlreadyExistsException | OwnerDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable long id) {
        log.info("GET Request -> get a property");
        Property prop = service.getProperty(id);
        if (prop == null){
            return new ResponseEntity<>(prop, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prop, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable long id, @RequestBody PropertyDTO propertyDTO) {
        log.info("POST Request -> Update a new Property");
        Property prop = service.updateProperty(id, propertyDTO);
        if (prop == null){
            return new ResponseEntity<>(prop, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(prop, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteProperty(@PathVariable long id) {
        log.info("DELETE Request -> Delete a new Property");

        int resp = service.deleteProperty(id);
        if (resp == 1){
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);

    }

        
}