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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import es.module2.smapi.datamodel.AlarmDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="alarm")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Alarm implements Serializable{


  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO) 
  @Column(name = "alarm_id", nullable = false)
  private long id;

  @Column(name = "private_id",nullable = false)
  private long privateId;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "property_id", referencedColumnName = "property_id")
  @JsonIgnoreProperties("alarms")
  @JsonIdentityReference(alwaysAsId = true)
  //@JsonIgnore
  private Property property;

  public Alarm(long private_id, Property property) {
    this.property = property;
    this.privateId = private_id;
  }

    public void convertDTOtoObject(AlarmDTO dto){
        this.setPrivateId(dto.getPrivateId());
        this.setProperty(dto.getPropertyName(),dto.getPropertyAddress());
    }
 

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getPrivateId() {
    return this.privateId;
  }

  public void setPrivateId(long privateId) {
    this.privateId = privateId;
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
        return id == alarm.id && Objects.equals(privateId, alarm.privateId) && Objects.equals(property, alarm.property);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, privateId, property);
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", private_id='" + getPrivateId() + "'" +
      ", property='" + getProperty() + "'" +
      "}";
  }

}