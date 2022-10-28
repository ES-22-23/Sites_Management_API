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


@Service
public class AlarmService {
    private static final Logger log = LoggerFactory.getLogger(AlarmService.class);

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private PropertyRepository propRepository;
    
    // CRUD Func Owner

    public Alarm createAlarm(AlarmDTO alarmDTO) throws AlarmAlreadyExistsException {
        log.info("Inserting Alarm");

        Property p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress()).orElse(null);

        if (p1==null){
            return null;
        }

        Alarm alarm = alarmRepository.findByPropertyAndPrivateId(p1, alarmDTO.getPrivateId()).orElse(null);

        if (alarm!=null){
            throw new AlarmAlreadyExistsException("Alarm already exists: " + alarm);
        }

        Alarm alarm2 = new Alarm();
        alarm2.convertDTOtoObject(alarmDTO);

        

        alarm2.setProperty(p1);
        alarm2.setPrivateId(alarm2.getId());
        p1.getAlarms().add(alarm2);

        return alarmRepository.saveAndFlush(alarm2);
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
        Alarm alarm2 = alarm;

        Property oldProp = propRepository.findByAlarms(alarm2.getId()).orElse(null);
        if (oldProp != null){
            oldProp.getAlarms().remove(alarm2.getId());
        }
        alarm2.convertDTOtoObject(alarmDTO);

        alarm2.setProperty(p1);
        alarm2.setPrivateId(alarm2.getId());
        p1.getAlarms().add(alarm2);


        return alarmRepository.saveAndFlush(alarm2);
    }

    public int  deleteAlarm(long id) {
        log.info("Deleting Alarm");
        return alarmRepository.deleteById(id);
    }

}