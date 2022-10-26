package es.module2.smapi.repository;
import es.module2.smapi.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findByPrivateId(long id);
    void deleteById(long id);
    @Transactional 
    Optional<Alarm> deleteByPrivateId(long id);
}