package austral.ing.lab1.model;


import austral.ing.lab1.entity.Cards;
import com.fasterxml.jackson.annotation.JsonBackReference;
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


  @Column(name = "Photo_Url")
  private String photoUrl;

  @Column(name = "Bio")
  private String bio;

  @Column(name = "Username")
  private String userName;

  @Column(name = "Recently_Modified")
  private Boolean recentlyModified;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonManagedReference
  @JoinTable(
          name = "User_Collection",
          joinColumns = {@JoinColumn(name = "UID")},
          inverseJoinColumns = {@JoinColumn(name = "ID")}
  )
  private List<Card> cards = new ArrayList<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonBackReference
  @JoinTable(
          name = "Friend_List",
          joinColumns = {@JoinColumn(name = "U_1")},
          inverseJoinColumns = {@JoinColumn(name = "U_2")}
  )
  private List<User> friendList = new ArrayList<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonBackReference
  @JoinTable(
          name = "Pending_Requests",
          joinColumns = {@JoinColumn(name = "Sender")},
          inverseJoinColumns = {@JoinColumn(name = "Receiver")}
  )
  private List<User> pendingRequests = new ArrayList<>();


  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonManagedReference
  @JoinTable(
          name = "User_Wishlist",
          joinColumns = {@JoinColumn(name = "UID")},
          inverseJoinColumns = {@JoinColumn(name = "ID")}
  )
  private List<Card> wishlist = new ArrayList<>();

  @Column(name = "Recently_Modified_Wishlist")
  private Boolean recentlyModifiedWishlist;

  public void addCard(Card card) {
    if (cards.contains(card)) {
    } else {
      if (wishlist.contains(card)) {
        removeCardWishlist(card);
      }
      recentlyModified = true;
      cards.add(card);
      card.getUsers().add(this);
    }
  }

  public void addToPending(User user) {
    if (friendList.contains(user)) {}
    else if(user.pendingRequests.contains(this)){
      this.friendList.add(user);
      user.friendList.add(this);
      user.pendingRequests.remove(this);
    }
    else{
    pendingRequests.add(user);
  }


}

public void addToFriendList(User user){
    if(friendList.contains(user)){}
    else{
      this.friendList.add(user);
      user.friendList.add(this);
      user.removeFromPending(this);
      this.removeFromPending(user);
    }

}

public void removeFromPending(User user){
    if(pendingRequests.contains(user)){
      pendingRequests.remove(user);
    }
}



  public void addCardToWishlist(Card card){
    if(wishlist.contains(card)||cards.contains(card)){}
    else {
      recentlyModifiedWishlist=true;
      wishlist.add(card);
      card.getUsers().add(this);
    }
  }


  public void removeCard(Card card){
    if(cards.contains(card)){
    cards.remove(card);
    card.getUsers().remove(this);
    }
  }

  public void removeCardWishlist(Card card){
    if(wishlist.contains(card)){
      wishlist.remove(card);
      card.getUsers2().remove(this);
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

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public List<Card> getWishlist() {
    return wishlist;
  }

  public void setWishlist(List<Card> wishlist) {
    this.wishlist = wishlist;
  }

  public Boolean getRecentlyModifiedWishlist() {
    return recentlyModifiedWishlist;
  }

  public void setRecentlyModifiedWishlist(Boolean recentlyModifiedWishlist) {
    this.recentlyModifiedWishlist = recentlyModifiedWishlist;
  }

  public List<User> getFriendList() {
    return friendList;
  }

  public void setFriendList(List<User> friendList) {
    this.friendList = friendList;
  }

  public List<User> getPendingRequests() {
    return pendingRequests;
  }

  public void setPendingRequests(List<User> pendingRequests) {
    this.pendingRequests = pendingRequests;
  }
}
