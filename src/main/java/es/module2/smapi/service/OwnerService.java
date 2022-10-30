package es.module2.smapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.datamodel.OwnerDTO;
import es.module2.smapi.exceptions.OwnerAlreadyExistsException;
import es.module2.smapi.model.Owner;
import es.module2.smapi.repository.OwnerRepository;

@Service
public class OwnerService {
    private static final Logger log = LoggerFactory.getLogger(OwnerService.class);

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Owner> getAllOwners(){
        log.info("Getting All Owners");

        return ownerRepository.findAll();
    }

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

    public Owner updateOwner(String username, OwnerDTO ownerDTO) {
        log.info("Updating Owner");

        Owner owner = ownerRepository.findByUsername(username).orElse(null);

        if (owner==null){
            return null;
        }

        owner.setEmail(ownerDTO.getEmail());
        owner.setName(ownerDTO.getName());
        
        return ownerRepository.saveAndFlush(owner);
    }

    public int deleteOwner(String username) {
        log.info("Deleting Owner");
        return ownerRepository.deleteByUsername(username);
    }

}