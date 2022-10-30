package es.module2.smapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.exceptions.PropertyAlreadyExistsException;
import es.module2.smapi.model.Property;
import es.module2.smapi.service.PropertyService;

@RestController
@RequestMapping("/properties")
@Validated
//@CrossOrigin
class PropertyController {
    private static final Logger log = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propService;


    @PostMapping("/newProperty")
    public ResponseEntity<Property> createProperty(@RequestBody PropertyDTO propertyDTO) {
        log.info("POST Request -> Store a new Property");
        Property prop = null;
        // return new ResponseEntity<>(new Property(), HttpStatus.CREATED);
        try {
            prop = propService.createProperty(propertyDTO);
            return new ResponseEntity<>(prop, HttpStatus.CREATED);
        } catch (PropertyAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getProperty")
    public ResponseEntity<Property> getProperty(@RequestParam  String name,String address) {
        log.info("GET Request -> get a property");
        Property prop = propService.getProperty(name, address);
        if (prop == null){
            return new ResponseEntity<>(prop, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prop, HttpStatus.OK);
    }

    @PostMapping("/updateProperty")
    public ResponseEntity<Property> updateProperty(@RequestBody PropertyDTO propertyDTO) {
        log.info("POST Request -> Update a new Property");
        Property prop = propService.updateProperty(propertyDTO);
        if (prop == null){
            return new ResponseEntity<>(prop, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(prop, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProperty")
    public ResponseEntity<Integer> deleteProperty(@RequestParam  String name,String address) {
        log.info("DELETE Request -> Delete a new Property");

        int resp = propService.deleteProperty(name, address);
        if (resp == 1){
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);

    }

        
}