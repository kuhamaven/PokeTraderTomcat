package austral.ing.lab1.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  @JsonManagedReference
  @JoinTable(
          name = "User_Collection",
          joinColumns = { @JoinColumn(name = "UID")},
          inverseJoinColumns = { @JoinColumn(name = "ID")}
  )
  private List<Card> cards = new ArrayList<>();

  public void addCard(Card card){
    cards.add(card);
    card.getUsers().add(this);
  }

  public void removeCard(Card card){
    cards.remove(card);
    card.getUsers().remove(this);
  }

  public List<Card> getCards(){
    return cards;
  }

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
