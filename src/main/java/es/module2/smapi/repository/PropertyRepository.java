package es.module2.smapi.repository;
import es.module2.smapi.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PropertyRepository extends JpaRepository<Property, Long> {
    Owner findByName(String name);
    Owner findByAddress(String address);
    long deleteById(long id);
}