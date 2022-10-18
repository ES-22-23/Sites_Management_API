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
@Table(name="property")
public class Property implements Serializable{


  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO) 
  @Column(name = "property_id", nullable = false)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;


  @Column(name = "address", nullable = false)
  private String address;


  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
  @JsonIgnoreProperties("properties")
  //@JsonIgnore
  private Owner owner;



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

  public Owner getOwner() {
    return this.owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
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





  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Property)) {
            return false;
        }
        Property property = (Property) o;
        // return id == property.id && Objects.equals(name, property.name) && Objects.equals(address, property.address) && Objects.equals(owner, property.owner);
        return id == property.id;
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