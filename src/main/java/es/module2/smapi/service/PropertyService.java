package es.module2.smapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.PropertyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Service
public class PropertyService {
    private static final Logger log = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRepository propRepository;

    
    
    // CRUD Func Owner

    public Property createProperty(PropertyDTO propDTO) {
        log.info("Inserting Property");

        Optional<Property> prop = propRepository.findByNameAndAddress(propDTO.getName(),propDTO.getAddress());

        if (prop.isPresent()){
            // throw new AddressAlreadyExistsException("Address already exists: " + address);
            return null;
        }

        Property prop2 = new Property();
        prop2.convertDTOtoObject(propDTO);

        return propRepository.saveAndFlush(prop2);
    }

    public Property getProperty(String address) {
        log.info("Getting Property");
        return propRepository.findByAddress(address);
    }

    public Property updateProperty(PropertyDTO propDTO) {
        log.info("Updating Property");

        Optional<Property> prop = propRepository.findByNameAndAddress(propDTO.getName(),propDTO.getAddress());


        prop.convertDTOtoObject(propDTO);

        return propRepository.saveAndFlush(prop);
    }
    public void deleteProperty(String name, String address) {
        log.info("Deleting Property");
        propRepository.deleteByNameAndAddress(name,address);
    }

}