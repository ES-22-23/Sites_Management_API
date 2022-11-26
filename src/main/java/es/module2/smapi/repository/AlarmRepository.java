package es.module2.smapi.repository;
import es.module2.smapi.model.Alarm;
import es.module2.smapi.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findByPropertyAndId(Property prop, long id);
    @Transactional 
    int deleteByPropertyAndId(Property prop, long id);
}