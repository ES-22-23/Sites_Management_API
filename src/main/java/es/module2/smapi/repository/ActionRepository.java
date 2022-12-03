package es.module2.smapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.module2.smapi.model.Action;

public interface ActionRepository extends JpaRepository<Action, String> {
    
}