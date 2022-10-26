package es.module2.smapi.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.module2.smapi.model.Property;


@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findByName(String name);
    Property findByAddress(String address);
    Optional<Property> findByNameAndAddress(String name, String address);
    void deleteById(long id);
}