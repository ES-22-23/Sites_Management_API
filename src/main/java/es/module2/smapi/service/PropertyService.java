package es.module2.smapi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.PropertyDTO;
import es.module2.smapi.exceptions.OwnerDoesNotExistException;
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
    
    public List<Property> getAllProperties(){
        log.info("Getting All Properties");

        return propRepository.findAll();
    }

    public Property createProperty(PropertyDTO propDTO) throws PropertyAlreadyExistsException, OwnerDoesNotExistException{
        log.info("Inserting Property");

        Property prop = propRepository.findByNameAndAddress(propDTO.getName(),propDTO.getAddress()).orElse(null);

        if (prop != null){
            throw new PropertyAlreadyExistsException("Property already exists: " + prop);
        }

        Property prop2 = new Property();
        prop2.convertDTOtoObject(propDTO);

        Owner ow1 = owRepository.findByUsername(propDTO.getOwnerUsername()).orElse(null);

        if (ow1== null){
            throw new OwnerDoesNotExistException("Owner does not exist: " + propDTO.getOwnerUsername());
        }

        prop2.setOwner(ow1);
        ow1.getProperties().add(prop2);
    
        return propRepository.saveAndFlush(prop2);
    }

    public Property getProperty(long id) {
        log.info("Getting Property");

        return propRepository.findById(id).orElse(null);

    }

    public Property updateProperty(long id, PropertyDTO propDTO) throws PropertyAlreadyExistsException, OwnerDoesNotExistException{
        log.info("Updating Property");

        Property prop = propRepository.findById(id).orElse(null);

        if (prop== null){
            throw new PropertyAlreadyExistsException("Property already exists: " + prop);
        }

        Owner oldOwner = owRepository.findByProperties(prop).orElse(null);

        if (oldOwner!=null){
            oldOwner.getProperties().remove(prop);
        }

        prop.setAddress(propDTO.getAddress());
        prop.setName(propDTO.getName());

        Owner ow1 = owRepository.findByUsername(propDTO.getOwnerUsername()).orElse(null);

        if (ow1==null){
            throw new OwnerDoesNotExistException("Owner does not exist: " + propDTO.getOwnerUsername());

        }
        
        prop.setOwner(ow1);
        ow1.getProperties().add(prop);

        return propRepository.saveAndFlush(prop);
    }
    public int deleteProperty(long id) {
        log.info("Deleting Property");

        Optional<Property> propertyOptional = propRepository.findById(id);

        if(propertyOptional.isEmpty()){
            return 0;
        }

        Property property = propertyOptional.get();
        Owner owner = owRepository.findByUsername(property.getOwner().getUsername()).get();

        owner.getProperties().remove(property);
        property.setOwner(null);

        owRepository.save(owner);
        propRepository.saveAndFlush(property);

        propRepository.deleteById(id);
        return  1;
    }
}