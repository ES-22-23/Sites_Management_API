package es.module2.smapi.repository;
import es.module2.smapi.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


public interface CameraRepository extends JpaRepository<Camera, Long> {
    Camera findById(long id);
    Alarm findByPrivateId(long id);
    
    @Transactional 
    Optional<Alarm> deleteByPrivateId(long id);
}