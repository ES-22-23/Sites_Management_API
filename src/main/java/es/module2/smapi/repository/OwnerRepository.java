package es.module2.smapi.repository;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByName(String name);
    Owner findByUsername(String username);
    @Transactional 
    long deleteByUsername(String username);
    Optional<Owner> findByProperties(Property prop);
}