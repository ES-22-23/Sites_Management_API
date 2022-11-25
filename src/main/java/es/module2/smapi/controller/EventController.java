package es.module2.smapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.service.EventService;

@RestController
@RequestMapping("/events")
@Validated
class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService service;

    @GetMapping()
    public ResponseEntity<List<String>> getAllVideos(){
        log.info("GET Request -> Get all videos");

        return new ResponseEntity<>(service.getAllVideoKeys(), HttpStatus.OK);
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<List<String>> getVideosFromOwner(@PathVariable String username) {
        log.info("GET Request -> Get videos from owner");

        return new ResponseEntity<>(service.getVideoKeysFromOwner(username), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<String>> getVideosFromProperty(@PathVariable long propertyId) {
        log.info("GET Request -> Get videos from Property");
        
        return new ResponseEntity<>(service.getVideoKeysFromProperty(propertyId), HttpStatus.OK);
    }

    @GetMapping("/camera/{cameraId}")
    public ResponseEntity<List<String>> getVideosFromCamera(@PathVariable long cameraId) {
        log.info("GET Request -> Get videos from Camera");
        
        return new ResponseEntity<>(service.getVideoKeysFromCamera(cameraId), HttpStatus.OK);
    }

    @GetMapping("/{videoKey}")
    public ResponseEntity<ByteArrayResource> getVideoFile(@PathVariable String videoKey) {
        log.info("GET Request -> Get Video");
        
        byte[] video = service.getVideoFile(videoKey);
        ByteArrayResource response = new ByteArrayResource(video);
        if (video == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity
                    .ok()
                    .contentLength(video.length)
                    .header("Content-type","application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + videoKey + "\"")
                    .body(response);
    }
}