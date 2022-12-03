package es.module2.smapi.model;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import es.module2.smapi.datamodel.CameraDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @Column(name = "camera_id", nullable = false, unique=true, length = 50)
  private String id;


  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @JoinColumn(name = "property_id", nullable = false)
  @JsonIgnoreProperties("cameras")
  @JsonIdentityReference(alwaysAsId = true)
  //@JsonIgnore
  private Property property;

  // public Camera(String id , Property property) {
  //   this.property=property;
  //   this.id=id;
  // }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void convertDTOtoObject(CameraDTO dto){
      this.setId(dto.getId());
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
        if (!(o instanceof Camera)) {
            return false;
        }
        Camera camera = (Camera) o;
        return id.equals(camera.getId())  && Objects.equals(property, camera.getProperty());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id,  property);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", property='" + getProperty() + "'" +
      "}";
  }
 
}