package es.module2.smapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.OwnerDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.repository.OwnerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Service
public class OwnerService {
    private static final Logger log = LoggerFactory.getLogger(OwnerService.class);

    @Autowired
    private OwnerRepository ownerRepository;

    
    
    // CRUD Func Owner

    public Owner createOwner(OwnerDTO ownerDTO) {
        log.info("Inserting Owner");

        Optional<Owner> owner = ownerRepository.findByUsername(ownerDTO.getUsername());

        if (owner.isPresent()){
            // throw new AddressAlreadyExistsException("Address already exists: " + address);
            return null;
        }

        Owner owner2 = new Owner();
        owner2.convertDTOtoObject(ownerDTO);

        return ownerRepository.saveAndFlush(owner2);
    }

    public Owner getOwner(String username) {
        log.info("Getting Owner");

        return ownerRepository.findByUsername(username);
    }

    public Owner updateOwner(OwnerDTO ownerDTO) {
        log.info("Updating Owner");

        Optional<Owner> owner = ownerRepository.findByUsername(ownerDTO.getUsername());

        owner.convertDTOtoObject(ownerDTO);

        return ownerRepository.saveAndFlush(owner2);
    }

    public void deleteOwner(String username) {
        log.info("Deleting Owner");
        ownerRepository.deleteByUsername(username);
    }

}