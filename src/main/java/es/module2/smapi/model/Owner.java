package es.module2.smapi.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import es.module2.smapi.datamodel.OwnerDTO;
import java.util.ArrayList;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="OWNER")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
public class Owner implements Serializable{

  @Id
  @Column(name = "username", nullable = false)
  private String username;

  // @Column(name = "password",nullable = false)
  // private String password;
  
  @Column(name = "name",nullable = false)
  private String name;

  @Column(name = "properties")
  @OneToMany(targetEntity = Property.class, mappedBy = "owner", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  @JsonIdentityReference(alwaysAsId = true)
  private List<Property> properties=new ArrayList<Property>();

  public Owner(String username,String name) {

    this.username=username;
    this.name=name;
  }




  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Property> getProperties() {
    return this.properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }



    public void convertDTOtoObject(OwnerDTO dto){
        this.setUsername(dto.getUsername());
        this.setName(dto.getName());
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
    return Objects.hash(username, name);
  }

  @Override
  public String toString() {
    return "{" +
      ", username='" + getUsername() + "'" +
      ", name='" + getName() + "'" +
      "}";
  }
  
}