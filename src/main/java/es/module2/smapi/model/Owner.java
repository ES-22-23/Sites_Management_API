package es.module2.smapi.model;


import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OWNER")
public class Owner {

  private @Id String username;
  private String password;
  private String name;

  public Owner(String username,String password,String name) {
    this.username=username;
    this.password=password;
    this.name=name;
  }


  public Owner() {

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