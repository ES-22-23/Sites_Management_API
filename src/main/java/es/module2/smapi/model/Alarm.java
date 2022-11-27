package es.module2.smapi.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import es.module2.smapi.datamodel.AlarmDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import es.module2.smapi.datamodel.AlarmDTO;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="alarm")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Alarm implements Serializable{


  @Id 
  @Column(name = "alarm_id", nullable = false, unique=true, length = 50)
  private String id;


  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @JoinColumn(name = "property_id", nullable = false)
  @JsonIgnoreProperties("alarms")
  @JsonIdentityReference(alwaysAsId = true)
  //@JsonIgnore
  private Property property;

  // public Alarm(long id, Property property) {
  //   this.property = property;
  //   this.id = id;
  // }

  public void convertDTOtoObject(AlarmDTO dto){
      this.setId(dto.getId());
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
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
        return id.equals(alarm.getId())  && Objects.equals(property, alarm.getProperty());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, property);
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", property='" + getProperty() + "'" +
      "}";
  }

}