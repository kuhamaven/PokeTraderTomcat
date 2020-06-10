package austral.ing.lab1.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

  @Column(name = "Email")
  private String email;

  @Id
  @Column(name = "UID")
  private String id;


  @Column(name="Photo_Url")
  private String photoUrl;

  @Column(name="Bio")
  private String bio;

  @Column(name="Username")
  private String userName;

  @Column(name="Recently_Modified")
  private Boolean recentlyModified;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonManagedReference
  @JoinTable(
          name = "User_Collection",
          joinColumns = { @JoinColumn(name = "UID")},
          inverseJoinColumns = { @JoinColumn(name = "ID")}
  )
  private List<Card> cards = new ArrayList<>();

  public void addCard(Card card){
    if(cards.contains(card)){}
    else {
      recentlyModified=true;
      cards.add(card);
      card.getUsers().add(this);
    }
  }

  public void removeCard(Card card){
    if(cards.contains(card)){
    cards.remove(card);
    card.getUsers().remove(this);
    }
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

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getRecentlyModified() {
    return recentlyModified;
  }

  public void setRecentlyModified(Boolean recentlyModified) {
    this.recentlyModified = recentlyModified;
  }
}
