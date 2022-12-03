package es.module2.smapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="actions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Action implements Serializable{

    @Id 
    @Column(name = "action_id", nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    private String date;

    private String admin;

    private String action_type;

    private String entity_type;

    private String entity_id;

    public Action(String date, String admin, String action_type, String entity_type, String entity_id) {
        this.date = date;
        this.admin = admin;
        this.action_type = action_type;
        this.entity_type = entity_type;
        this.entity_id = entity_id;
    }
}