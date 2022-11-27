package es.module2.smapi.repository;
import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Property;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


public interface CameraRepository extends JpaRepository<Camera, String> {
    Optional<Camera> findByPropertyAndId(Property prop, String id);
    @Transactional 
    int deleteByPropertyAndId(Property prop, String id);
}