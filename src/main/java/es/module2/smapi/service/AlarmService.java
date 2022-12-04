package es.module2.smapi.service;

import java.util.List;
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

    public List<Alarm> getAllAlarms(){
        log.info("Getting All Alarms");

        return alarmRepository.findAll();
    }
    
    public Alarm createAlarm(AlarmDTO alarmDTO) throws AlarmAlreadyExistsException, PropertyDoesNotExistException {
        log.info("Inserting Alarm");

        Property p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }

        Alarm alarm = alarmRepository.findByPropertyAndId(p1, alarmDTO.getId()).orElse(null);

        if (alarm!=null){
            throw new AlarmAlreadyExistsException("Alarm already exists: " + alarm);
        }

        Alarm alarm2 = new Alarm();

        alarm2.setProperty(p1);
        alarm2.setId(alarmDTO.getId());
        p1.getAlarms().add(alarm2);
        alarm2 = alarmRepository.saveAndFlush(alarm2);
        return alarm2;
    }

    public Alarm getAlarm(String id) {
        log.info("Getting Alarm");

        return alarmRepository.findById(id).orElse(null);

    }

    public Alarm updateAlarm(String id, AlarmDTO alarmDTO) throws AlarmAlreadyExistsException, PropertyDoesNotExistException{
        log.info("Updating Alarm");

        Property p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            throw new PropertyDoesNotExistException("Property does not exist: " + p1);
        }

        Alarm alarm = alarmRepository.findById(id).orElse(null);

        if (alarm== null){
            throw new AlarmAlreadyExistsException("Alarm already exists: " + alarm);
        }

        alarm.getProperty().getAlarms().remove(alarm);

        alarm.setProperty(p1);
        p1.getAlarms().add(alarm);

        return alarmRepository.saveAndFlush(alarm);
    }

    public int deleteAlarm(String id) {
        log.info("Deleting Alarm");

        Optional<Alarm> alarmOptional = alarmRepository.findById(id);

        if(alarmOptional.isEmpty()){
            return 0;
        }

        Alarm alarm = alarmOptional.get();
        Property property = propRepository.findById(alarm.getProperty().getId()).get();

        property.getAlarms().remove(alarm);
        alarm.setProperty(null);

        propRepository.save(property);
        alarmRepository.saveAndFlush(alarm);

        alarmRepository.deleteById(id);
        return  1;
    }

}