package es.module2.smapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.datamodel.CameraDTO;
import es.module2.smapi.model.Camera;
import es.module2.smapi.service.CameraService;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/cameras")
@Validated
class CameraController {
    private static final Logger log = LoggerFactory.getLogger(CameraController.class);


    @Autowired
    private CameraService camService;


    @PostMapping("/newCamera")
    Camera createCamera(@RequestBody CameraDTO newCamera) {
        return camService.createCamera(newCamera);
    }

    @GetMapping("/getCamera")
    Camera getCamera(@RequestParam  long privateId) {
        return camService.getCamera(privateId);
    }

    @PostMapping("/updateCamera")
    Camera updateCamera(@RequestBody CameraDTO newCamera) {
        return camService.updateCamera(newCamera);
    }

    @DeleteMapping("/deleteCamera")
    void deleteCamera(@RequestParam long privateId) {
        camService.deleteCamera(privateId);
    }

        
}