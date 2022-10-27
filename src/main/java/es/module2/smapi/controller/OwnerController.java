package es.module2.smapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.validation.annotation.Validated;

import es.module2.smapi.model.Owner;
import es.module2.smapi.service.OwnerService;
import es.module2.smapi.datamodel.OwnerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/owners")
@Validated
class OwnerController {

    @Autowired
    private OwnerService owService;


    
    // // Owner endpoints
    // @PostMapping("/newOwner")
    // Owner createOwner(@RequestBody OwnerDTO newOwner) {
    //     return owService.createOwner(newOwner);
    // }

    // @GetMapping("/getOwner")
    // Owner getOwner(@RequestParam  String username) {
    //     return owService.getOwner(username);
    // }

    // @PostMapping("/updateOwner")
    // Owner updateOwner(@RequestBody OwnerDTO newOwner) {
    //     return owService.updateOwner(newOwner);
    // }
    // @DeleteMapping("/deleteOwner")
    // void deleteOwner(@RequestBody String username) {
    //     owService.deleteOwner(username);
    // }



}