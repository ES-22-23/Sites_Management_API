package es.module2.smapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.model.Action;
import es.module2.smapi.service.ActionService;

@RestController
@RequestMapping("/actions")
@Validated
public class ActionController {
    private static final Logger log = LoggerFactory.getLogger(ActionController.class);

    @Autowired
    private ActionService actionService;
    
    @GetMapping()
    public ResponseEntity<List<Action>> getAllActions(){
        log.info("GET Request -> Get all actions");

        return new ResponseEntity<>(actionService.getAllActions(), HttpStatus.OK);
    }
}
