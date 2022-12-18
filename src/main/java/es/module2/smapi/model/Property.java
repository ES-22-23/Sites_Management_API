package es.module2.smapi.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import es.module2.smapi.datamodel.PropertyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="property")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Property implements Serializable{


  @Id 
  @Column(name = "property_id", nullable = false, unique=true)
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private long id;

  @Column(name = "name", nullable = false)
  private String name;


  @Column(name = "address", nullable = false)
  private String address;


  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "username", nullable = true)
  @JsonIdentityReference(alwaysAsId = true)
  private Owner owner;



  @Column(name = "cameras")
  @OneToMany(targetEntity = Camera.class, mappedBy = "property", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  @JsonIdentityReference(alwaysAsId = true)
  private List<Camera> cameras= new ArrayList<Camera>();



  @Column(name = "alarms")
  @OneToMany(targetEntity = Alarm.class, mappedBy = "property", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  @JsonIdentityReference(alwaysAsId = true)
  private List<Alarm> alarms=new ArrayList<Alarm>();

  public Property(String address,String name,Owner owner) {
    this.address=address;
    this.name=name;
    this.owner=owner;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Owner getOwner() {
    return this.owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }


  public List<Camera> getCameras() {
    return this.cameras;
  }

  public void setCameras(List<Camera> cameras) {
    this.cameras = cameras;
  }

  public List<Alarm> getAlarms() {
    return this.alarms;
  }

  public void setAlarms(List<Alarm> alarms) {
    this.alarms = alarms;
  }

    public void convertDTOtoObject(PropertyDTO dto){
        this.setAddress(dto.getAddress());
        this.setName(dto.getName());
    }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Property)) {
            return false;
        }
        Property property = (Property) o;
        // return id == property.id && Objects.equals(name, property.name) && Objects.equals(address, property.address) && Objects.equals(owner, property.owner);
        return id ==property.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, address, owner);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", address='" + getAddress() + "'" +
      ", owner='" + getOwner() + "'" +
      "}";
  }


 
}