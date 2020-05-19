package austral.ing.lab1.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

  @Column(name = "EMAIL")
  private String email;

  @Id
  @Column(name = "UID")
  private String id;

  @Column(name = "PASSWORD")
  private String password;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
          name = "User_Collection",
          joinColumns = { @JoinColumn(name = "UID")},
          inverseJoinColumns = { @JoinColumn(name = "ID")}
  )
  private List<Card> cards = new ArrayList<>();

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
