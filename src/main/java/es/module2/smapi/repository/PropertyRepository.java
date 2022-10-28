package es.module2.smapi.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.module2.smapi.model.Property;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Alarm;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByName(String name);
    Optional<Property> findByAddress(String address);
    Optional<Property> findByNameAndAddress(String name, String address);
    @Transactional 
    int deleteByNameAndAddress(String name, String address);
    
    Optional<Property> findByCameras(long cam);
    Optional<Property> findByAlarms(long al);


    // find property by alarm id
}