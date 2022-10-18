package es.module2.smapi.repository;
import es.module2.smapi.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findByName(String name);
    Property findByAddress(String address);
    void deleteById(long id);
}