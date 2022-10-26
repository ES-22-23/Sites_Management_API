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




@Service
public class AlarmService {
    private static final Logger log = LoggerFactory.getLogger(AlarmService.class);

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private PropertyRepository propRepository;
    
    // CRUD Func Owner

    public Alarm createAlarm(AlarmDTO alarmDTO) {
        log.info("Inserting Alarm");

        Optional<Alarm> alarm = alarmRepository.findByPrivateId(alarmDTO.getPrivateId());

        if (alarm.isPresent()){
            // throw new AddressAlreadyExistsException("Address already exists: " + address);
            return null;
        }

        Alarm alarm2 = new Alarm();
        alarm2.convertDTOtoObject(alarmDTO);

        Optional<Property> p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress());

        if (p1.isPresent()){
            return null;
        }

        alarm2.setProperty(p1.get());
        p1.get().getAlarms().add(alarm2);

        return alarmRepository.saveAndFlush(alarm2);
    }

    public Alarm getAlarm(long privateId) {
        log.info("Getting Alarm");

        Optional<Alarm> alarm = alarmRepository.findByPrivateId(privateId);
        
        if (alarm.isPresent()){
            // throw new AddressAlreadyExistsException("Address already exists: " + address);
            return null;
        }

        return alarm.get();
    }

    public Alarm updateAlarm(AlarmDTO alarmDTO) {
        log.info("Updating Alarm");
        
        Optional<Alarm> alarm = alarmRepository.findByPrivateId(alarmDTO.getPrivateId());


        if (alarm.isPresent()){
            // throw new AddressAlreadyExistsException("Address already exists: " + address);
            return null;
        }
        Alarm alarm2 = alarm.get();

        Optional<Property> oldProp = propRepository.findByAlarms(alarm2);
        if (oldProp.isPresent()){
            oldProp.get().getAlarms().remove(alarm2);
        }
        alarm2.convertDTOtoObject(alarmDTO);

        Optional<Property> p1 = propRepository.findByNameAndAddress(alarmDTO.getPropertyName(), alarmDTO.getPropertyAddress());

        if (p1.isEmpty()){
            return null;
        }

        alarm2.setProperty(p1.get());
        p1.get().getAlarms().add(alarm2);


        return alarmRepository.saveAndFlush(alarm2);
    }

    public void deleteAlarm(long private_id) {
        log.info("Deleting Alarm");
        alarmRepository.deleteByPrivateId(private_id);
    }

}