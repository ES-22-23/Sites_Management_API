package es.module2.smapi.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.module2.smapi.model.Property;



@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
    Optional<Property> findByName(String name);
    Optional<Property> findByAddress(String address);
    Optional<Property> findByNameAndAddress(String name, String address);
    @Transactional 
    int deleteByNameAndAddress(String name, String address);
    
    Optional<Property> findByCameras(String cam);
    Optional<Property> findByAlarms(String al);


    // find property by alarm id
}