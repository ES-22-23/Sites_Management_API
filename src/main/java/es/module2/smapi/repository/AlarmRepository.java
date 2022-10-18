package es.module2.smapi.repository;
import es.module2.smapi.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Alarm findById(long id);
    void deleteById(long id);
}