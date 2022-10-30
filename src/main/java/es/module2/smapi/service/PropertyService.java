package es.module2.smapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.exceptions.PropertyAlreadyExistsException;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.repository.PropertyRepository;




@Service
public class PropertyService {
    private static final Logger log = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRepository propRepository;

    @Autowired
    private OwnerRepository owRepository;
    
    
    // CRUD Func Owner

    public Property createProperty(PropertyDTO propDTO) throws PropertyAlreadyExistsException{
        log.info("Inserting Property");

        Property prop = propRepository.findByNameAndAddress(propDTO.getName(),propDTO.getAddress()).orElse(null);

        if (prop != null){
            throw new PropertyAlreadyExistsException("Property already exists: " + prop);
        }

        Property prop2 = new Property();
        prop2.convertDTOtoObject(propDTO);

        Owner ow1 = owRepository.findByUsername(propDTO.getOwnerUsername()).orElse(null);

        if (ow1== null){
            return null;
        }

        prop2.setOwner(ow1);
        ow1.getProperties().add(prop2);
    
        return propRepository.saveAndFlush(prop2);
    }

    public Property getProperty(String name, String address) {
        log.info("Getting Property");

        return propRepository.findByNameAndAddress(name, address).orElse(null);

    }

    public Property updateProperty(PropertyDTO propDTO) {
        log.info("Updating Property");

        Property prop = propRepository.findByNameAndAddress(propDTO.getName(),propDTO.getAddress()).orElse(null);


        if (prop== null){
            return null;
        }



        Owner oldOwner = owRepository.findByProperties(prop).orElse(null);
        if (oldOwner!=null){
            oldOwner.getProperties().remove(prop);
        }

        prop.convertDTOtoObject(propDTO);


        Owner ow1 = owRepository.findByUsername(propDTO.getOwnerUsername()).orElse(null);

        if (ow1==null){
            return null;
        }

        prop.setOwner(ow1);
        ow1.getProperties().add(prop);

        return propRepository.saveAndFlush(prop);
    }
    public int deleteProperty(String name, String address) {
        log.info("Deleting Property");
        return  propRepository.deleteByNameAndAddress(name,address);
    }

}