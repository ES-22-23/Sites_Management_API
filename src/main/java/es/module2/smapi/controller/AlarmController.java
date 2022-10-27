package es.module2.smapi.controller;

import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.service.AlarmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.validation.annotation.Validated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.module2.smapi.exceptions.AlarmAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/alarms")
@Validated
class AlarmController {
    private static final Logger log = LoggerFactory.getLogger(AlarmController.class);


    @Autowired
    private AlarmService service;


    @PostMapping("/newAlarm")
    public ResponseEntity<Alarm> createAlarm(@RequestBody AlarmDTO alarmDTO) {
        log.info("POST Request -> Store a new Alarm");
        Alarm al = null;
        try {
            al = service.createAlarm(alarmDTO);
            return new ResponseEntity<>(al, HttpStatus.CREATED);
        } catch (AlarmAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAlarm")
    public ResponseEntity<Alarm> getAlarm(@RequestParam  long privateId) {
        log.info("GET Request -> get a Alarm");
        Alarm al = service.getAlarm(privateId);
        if (al == null){
            return new ResponseEntity<>(al, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(al, HttpStatus.OK);
    }

    @PostMapping("/updateAlarm")
    public ResponseEntity<Alarm> updateAlarm(@RequestBody AlarmDTO alarmDTO) {
        log.info("POST Request -> Update a new Alarm");
        Alarm al = service.updateAlarm(alarmDTO);
        if (al == null){
            return new ResponseEntity<>(al, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(al, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAlarm")
    public ResponseEntity<Integer> deleteAlarm(@RequestParam long privateId) {
        log.info("DELETE Request -> Delete a new Alarm");

        int resp = service.deleteAlarm(privateId);
        if (resp == 1){
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    

        
}

