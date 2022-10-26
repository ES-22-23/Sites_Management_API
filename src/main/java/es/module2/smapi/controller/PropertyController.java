package es.module2.smapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.model.Property;
import es.module2.smapi.service.PropertyService;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/properties")
@Validated
class PropertyController {
    private static final Logger log = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propService;


    @PostMapping("/newProperty")
    Property createProperty(@RequestBody PropertyDTO newProperty) {
        log.info("POST Request -> Store a new Property");

        return propService.createProperty(newProperty);
    }

    @GetMapping("/getProperty")
    Property getProperty(@RequestParam  String name,String address) {
        log.info("GET Request -> get a property");

        return propService.getProperty(name, address);
    }

    @PostMapping("/updateProperty")
    Property updateProperty(@RequestBody PropertyDTO newProperty) {
        log.info("POST Request -> Update a new Property");
        return propService.updateProperty(newProperty);
    }

    @DeleteMapping("/deleteProperty")
    void deleteProperty(@RequestParam  String name,String address) {
        log.info("DELETE Request -> Delete a new Property");

        propService.deleteProperty(name, address);
    }

        
}