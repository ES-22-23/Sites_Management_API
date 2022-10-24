package es.module2.smapi.controller;

import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.service.SMAPIService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;




@RestController
class SMAPIController {

    @Autowired
    private SMAPIService service;
    
    

    // Owner endpoints
    @PostMapping("/newOwner")
    Owner createOwner(@RequestBody Owner newOwner) {
        return service.createOwner(newOwner);
    }

    @GetMapping("/getOwner")
    Owner getOwner(@RequestParam  String username) {
        return service.getOwner(username);
    }

    @PostMapping("/updateOwner")
    Owner updateOwner(@RequestBody Owner newOwner) {
        return service.updateOwner(newOwner);
    }
    @DeleteMapping("/deleteOwner")
    void deleteOwner(@RequestBody String username) {
        service.deleteOwner(username);
    }



    // Property endpoints

    @PostMapping("/newProperty")
    Property createProperty(@RequestBody Property newProperty) {
        return service.createProperty(newProperty);
    }

    @GetMapping("/getProperty")
    Property getProperty(@RequestParam  String address) {
        return service.getProperty(address);
    }

    @PostMapping("/updateProperty")
    Property updateProperty(@RequestBody Property newProperty) {
        return service.updateProperty(newProperty);
    }

    @DeleteMapping("/deleteProperty")
    void deleteProperty(@RequestParam long id) {
        service.deleteProperty(id);
    }

        // Camera endpoints

    @PostMapping("/newCamera")
    Camera createCamera(@RequestBody Camera newCamera) {
        return service.createCamera(newCamera);
    }

    @GetMapping("/getCamera")
    Camera getCamera(@RequestParam  long id) {
        return service.getCamera(id);
    }

    @PostMapping("/updateCamera")
    Camera updateCamera(@RequestBody Camera newCamera) {
        return service.updateCamera(newCamera);
    }

    @DeleteMapping("/deleteCamera")
    void deleteCamera(@RequestParam long id) {
        service.deleteCamera(id);
    }

    // Alarm endpoints

    @PostMapping("/newAlarm")
    Alarm createAlarm(@RequestBody Alarm newAlarm) {
        return service.createAlarm(newAlarm);
    }

    @GetMapping("/getAlarm")
    Alarm getAlarm(@RequestParam  long id) {
        return service.getAlarm(id);
    }

    @PostMapping("/updateAlarm")
    Alarm updateAlarm(@RequestBody Alarm newAlarm) {
        return service.updateAlarm(newAlarm);
    }

    @DeleteMapping("/deleteAlarm")
    void deleteAlarm(@RequestParam long id) {
        service.deleteAlarm(id);
    }
}