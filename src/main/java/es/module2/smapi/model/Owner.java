package es.module2.smapi.model;

import java.io.Serializable;
import java.util.ArrayList;
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

import es.module2.smapi.datamodel.OwnerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="OWNER")
//@JsonIdentityInfo(generator = ObjectIdGenerators.OwnerGenerator.class, owner = "username")
public class Owner implements Serializable{

  @Id
  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "email",nullable = false)
  private String email;
  
  @Column(name = "name",nullable = false)
  private String name;

  @Column(name = "properties")
  @OneToMany(targetEntity = Property.class, mappedBy = "owner", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  @JsonIdentityReference(alwaysAsId = true)
  private List<Property> properties=new ArrayList<Property>();

  public Owner(String username, String email, String name){
    this.username = username;
    this.email = email;
    this.name = name;
  }

  public void convertDTOtoObject(OwnerDTO dto){
      this.setUsername(dto.getUsername());
      this.setEmail(dto.getEmail());
      this.setName(dto.getName());
  }


  @Override
  public String toString() {
    return "{" +
      " username='" + getUsername() + "'" +
      ", email='" + getEmail() + "'" +
      ", name='" + getName() + "'" +
      "}";
  }

}