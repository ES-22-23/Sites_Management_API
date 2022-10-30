package es.module2.smapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.AlarmDTO;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.repository.AlarmRepository;

import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.model.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import es.module2.smapi.exceptions.AlarmAlreadyExistsException;
import es.module2.smapi.exceptions.PropertyDoesNotExistException;


@Service
public class AlarmService {
    private static final Logger log = LoggerFactory.getLogger(AlarmService.class);

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private PropertyRepository propRepository;
    
    public Alarm createAlarm(AlarmDTO alarmDTO) throws AlarmAlreadyExistsException, PropertyDoesNotExistException {
        log.info("Inserting Alarm");

        Property p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }

        Alarm alarm = alarmRepository.findByPropertyAndPrivateId(p1, alarmDTO.getPrivateId()).orElse(null);

        if (alarm!=null){
            throw new AlarmAlreadyExistsException("Alarm already exists: " + alarm);
        }

        Alarm alarm2 = new Alarm();

        alarm2.setProperty(p1);
        alarm2.setPrivateId(alarmDTO.getPrivateId());
        p1.getAlarms().add(alarm2);
        alarm2 = alarmRepository.saveAndFlush(alarm2);
        return alarm2;
    }

    public Alarm getAlarm(long id) {
        log.info("Getting Alarm");

        return alarmRepository.findById(id).orElse(null);

    }

    public Alarm updateAlarm(AlarmDTO alarmDTO) {
        log.info("Updating Alarm");

        Property p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            return null;
        }

        Alarm alarm = alarmRepository.findByPropertyAndPrivateId(p1,alarmDTO.getPrivateId()).orElse(null);

        if (alarm== null){
            return null;
        }

        alarm.getProperty().getAlarms().remove(alarm);

        alarm.setProperty(p1);
        p1.getAlarms().add(alarm);

        return alarmRepository.saveAndFlush(alarm);
    }

    public int deleteAlarm(long id) {
        log.info("Deleting Alarm");

        Optional<Alarm> alarm = alarmRepository.findById(id);

        if(alarm.isEmpty()){
            return 0;
        }
        alarmRepository.deleteById(id);
        return 1;
    }

}