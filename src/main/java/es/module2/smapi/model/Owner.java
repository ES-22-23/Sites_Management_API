package es.module2.smapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="OWNER")
public class Owner implements Serializable{

  @Id
  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password",nullable = false)
  private String password;
  
  @Column(name = "name",nullable = false)
  private String name;

  @Column(name = "properties")
  @OneToMany(targetEntity = Property.class, mappedBy = "owner", fetch = FetchType.EAGER,
          cascade = CascadeType.ALL)
  private Set<Property> properties;

  public Owner(String username,String password,String name) {

    this.username=username;
    this.password=password;
    this.name=name;
  }

  public Set<Property> getProperties() {
    return this.properties;
  }

  public void setProperties(Set<Property> properties) {
    this.properties = properties;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Owner)) {
            return false;
        }
        Owner owner = (Owner) o;
        return  Objects.equals(username, owner.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, name);
  }

  @Override
  public String toString() {
    return "{" +
      ", username='" + getUsername() + "'" +
      ", password='" + getPassword() + "'" +
      ", name='" + getName() + "'" +
      "}";
  }
  
}