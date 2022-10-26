package es.module2.smapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.service.AlarmService;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.validation.annotation.Validated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/alarms")
@Validated
class AlarmController {
    private static final Logger log = LoggerFactory.getLogger(AlarmController.class);


    @Autowired
    private AlarmService alService;


    @PostMapping("/newAlarm")
    Alarm createAlarm(@RequestBody AlarmDTO newAlarm) {
        return AlarmService.createAlarm(newAlarm);
    }

    @GetMapping("/getAlarm")
    Alarm getAlarm(@RequestParam  long privateId) {
        return AlarmService.getAlarm(privateId);
    }

    @PostMapping("/updateAlarm")
    Alarm updateAlarm(@RequestBody AlarmDTO newAlarm) {
        return AlarmService.updateAlarm(newAlarm);
    }

    @DeleteMapping("/deleteAlarm")
    void deleteAlarm(@RequestParam long privateId) {
        AlarmService.deleteAlarm(privateId);
    }

        
}