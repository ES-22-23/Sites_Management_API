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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.TokenAccess;
import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.exceptions.AlarmAlreadyExistsException;
import es.module2.smapi.exceptions.PropertyDoesNotExistException;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.service.ActionService;
import es.module2.smapi.service.AlarmService;

@RestController
@RequestMapping("/alarms")
@Validated
public class AlarmController {
    private static final Logger log = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private AlarmService service;

    @Autowired
    private ActionService actionService;

    private TokenAccess tokenAccess = new TokenAccess();

    @GetMapping()
    public ResponseEntity<List<Alarm>> getAllAlarms(){
        log.info("GET Request -> Get all alarms");

        return new ResponseEntity<>(service.getAllAlarms(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Alarm> createAlarm(@RequestBody AlarmDTO alarmDTO) {
        log.info("POST Request -> Store a new Alarm");
        try {
            Alarm al = service.createAlarm(alarmDTO);
            log.info(al.toString());

            String admin = tokenAccess.getUsername();

            actionService.createAction(admin, "CREATE", "Alarm", al.getId());

            return new ResponseEntity<>(al, HttpStatus.CREATED);
        } catch (AlarmAlreadyExistsException | PropertyDoesNotExistException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alarm> getAlarm(@PathVariable String id) {
        log.info("GET Request -> get a Alarm");
        Alarm al = service.getAlarm(id);
        if (al == null){
            log.error("Alarm -> This Alarm doesn't exist");
            return new ResponseEntity<>(al, HttpStatus.NOT_FOUND);
        }
        log.info(al.toString());
        return new ResponseEntity<>(al, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Alarm> updateAlarm(@PathVariable String id, @RequestBody AlarmDTO alarmDTO) {
        log.info("POST Request -> Update a new Alarm");
        try {
            Alarm al= service.updateAlarm(id, alarmDTO);
            log.info(al.toString());

            String admin = tokenAccess.getUsername();

            actionService.createAction(admin, "UPDATE", "Alarm", al.getId());

            return new ResponseEntity<>(al, HttpStatus.OK);

        } catch (AlarmAlreadyExistsException | PropertyDoesNotExistException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteAlarm(@PathVariable String id) {
        log.info("DELETE Request -> Delete a new Alarm");

        String admin = tokenAccess.getUsername();

        actionService.createAction(admin, "DELETE", "Alarm", id);

        int resp = service.deleteAlarm(id);
        if (resp == 1){
            log.error("Alarm -> This Alarm doesn't exist");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }     
}

