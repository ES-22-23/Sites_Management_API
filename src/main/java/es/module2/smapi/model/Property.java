package es.module2.smapi.model;


import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

package com.attacomsian.jpa.one2many.domains;

import javax.persistence.*;

@Entity
@Table(name="PROPERTY")
public class Property implements Serializable{
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  @Column(name = "property_id")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "username", nullable = false)
  private Owner owner;


  public Property() {
  }
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
        return id == property.id
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