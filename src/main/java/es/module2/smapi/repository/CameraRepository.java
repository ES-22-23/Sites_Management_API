package es.module2.smapi.repository;
import es.module2.smapi.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CameraRepository extends JpaRepository<Camera, Long> {
    Camera findById(long id);
    void deleteById(long id);
}