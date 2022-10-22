package es.module2.smapi.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import javax.persistence.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="alarm")
public class Alarm implements Serializable{


  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO) 
  @Column(name = "alarm_id", nullable = false)
  private long id;



  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "property_id", referencedColumnName = "property_id")
  @JsonIgnoreProperties("alarms")
  //@JsonIgnore
  private Property property;

  public Alarm(Property property) {
    this.property=property;
  }


  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Property getProperty() {
    return this.property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }




  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Alarm)) {
            return false;
        }
        Alarm alarm = (Alarm) o;
        return id == alarm.id ;
  }


  @Override
  public int hashCode() {
    return Objects.hash(id, property);
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      "}";
  }

 
}