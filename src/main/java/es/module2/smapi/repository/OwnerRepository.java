package es.module2.smapi.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    Optional<Owner> findByName(String name);
    Optional<Owner> findByUsername(String username);
    @Transactional 
    int deleteByUsername(String username);
    Optional<Owner> findByProperties(Property prop);
}