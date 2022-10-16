package es.module2.smapi.repository;
import es.module2.smapi.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByName(String name);
    List<Owner> findByUsername(String username);
    long deleteByUsername(String username);
}