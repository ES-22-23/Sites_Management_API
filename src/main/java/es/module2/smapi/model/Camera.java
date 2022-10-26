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

import es.module2.smapi.datamodel.CameraDTO;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="camera")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
public class Camera implements Serializable{


  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO) 
  @Column(name = "camera_id", nullable = false)
  private long id;

  @Column(name = "private_id",nullable = false)
  private long privateId;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "property_id", referencedColumnName = "property_id")
  @JsonIgnoreProperties("cameras")
  @JsonIdentityReference(alwaysAsId = true)
  //@JsonIgnore
  private Property property;

  public Camera(long privateId, Property property) {
    this.property=property;
    this.privateId=privateId;
  }


    public void convertDTOtoObject(CameraDTO dto){
        this.setId(dto.getId());
        this.setPrivateId(dto.getPrivateId());
        this.setProperty(dto.getProperty());
    }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Camera)) {
            return false;
        }
        Camera camera = (Camera) o;
        return id == camera.id && privateId == camera.privateId && Objects.equals(property, camera.property);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, privateId, property);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", privateId='" + getPrivateId() + "'" +
      ", property='" + getProperty() + "'" +
      "}";
  }
 
}