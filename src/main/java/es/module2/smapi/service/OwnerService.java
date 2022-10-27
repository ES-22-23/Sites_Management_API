package es.module2.smapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.OwnerDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.repository.OwnerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.module2.smapi.exceptions.OwnerAlreadyExistsException;




@Service
public class OwnerService {
    private static final Logger log = LoggerFactory.getLogger(OwnerService.class);

    @Autowired
    private OwnerRepository ownerRepository;

    
    
    // CRUD Func Owner

    public Owner createOwner(OwnerDTO ownerDTO) throws OwnerAlreadyExistsException{
        log.info("Inserting Owner");

        Owner owner = ownerRepository.findByUsername(ownerDTO.getUsername()).orElse(null);

        if (owner != null){
            throw new OwnerAlreadyExistsException("Owner already exists: " + owner);
        }

        Owner owner2 = new Owner();
        owner2.convertDTOtoObject(ownerDTO);

        return ownerRepository.saveAndFlush(owner2);
    }

    public Owner getOwner(String username) {
        log.info("Getting Owner");

        Owner owner = ownerRepository.findByUsername(username).orElse(null);
        return owner;
    }

    public Owner updateOwner(OwnerDTO ownerDTO) {
        log.info("Updating Owner");

        Owner owner = ownerRepository.findByUsername(ownerDTO.getUsername()).orElse(null);


        if (owner==null){
            return null;
        }

        Owner owner2 = new Owner();
        owner2.convertDTOtoObject(ownerDTO);

        return ownerRepository.saveAndFlush(owner2);
    }

    public void deleteOwner(String username) {
        log.info("Deleting Owner");
        ownerRepository.deleteByUsername(username);
    }

}