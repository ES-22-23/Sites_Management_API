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

import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.exceptions.CameraAlreadyExistsException;
import es.module2.smapi.exceptions.CameraDoesNotExistException;
import es.module2.smapi.exceptions.PropertyDoesNotExistException;
import es.module2.smapi.model.Camera;
import es.module2.smapi.service.CameraService;

@RestController
@RequestMapping("/cameras")
@Validated
class CameraController {
    private static final Logger log = LoggerFactory.getLogger(CameraController.class);

    @Autowired
    private CameraService service;

    @PostMapping("/newCamera")
    public ResponseEntity<Camera> createCamera(@RequestBody CameraDTO cameraDTO) {
        log.info("POST Request -> Store a new Camera");
        Camera cam = null;
        try {
            cam = service.createCamera(cameraDTO);
            return new ResponseEntity<>(cam, HttpStatus.CREATED);
        } catch (CameraAlreadyExistsException | PropertyDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCamera")
    public ResponseEntity<Camera> getCamera(@RequestParam  long id) {
        log.info("GET Request -> get a Camera");
        Camera cam = service.getCamera(id);
        if (cam == null){
            return new ResponseEntity<>(cam, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cam, HttpStatus.OK);
    }

    @PostMapping("/updateCamera")
    public ResponseEntity<Camera> updateCamera(@RequestBody CameraDTO cameraDTO) {
        log.info("POST Request -> Update a new Camera");
        Camera cam;
        try {
            cam = service.updateCamera(cameraDTO);
            return new ResponseEntity<>(cam, HttpStatus.OK);
        } catch (PropertyDoesNotExistException | CameraDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/deleteCamera")
    public ResponseEntity<Integer> deleteCamera(@RequestParam long id) {
        log.info("DELETE Request -> Delete a new Camera");

        int resp = service.deleteCamera(id);
        if (resp == 1){
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }
}