package austral.ing.lab1.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card {

    @Column(name = "Name")
    private String name;

    @Column(name = "Image_Url")
    private String imageUrl;

    @Id
    private String id;

    @Column(name = "Types")
    private String[] types;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "supertype")
    private String supertype;

    @Column(name = "national_Pokedex_Number")
    private int nationalPokedexNumber;

    @ManyToMany(mappedBy = "cards")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    @ManyToMany(mappedBy = "wishlist")
    @JsonBackReference
    private List<User> users2 = new ArrayList<>();

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Trade> tradesWhereOffered;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Bid> bids;

    @ManyToMany(mappedBy ="willingToAccept")
    @JsonBackReference
    private List<Trade> tradesWhereAccepted;

    public List<User> getUsers(){
        return users;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSupertype() {
        return supertype;
    }

    public void setSupertype(String supertype) {
        this.supertype = supertype;
    }

    public int getNationalPokedexNumber() {
        return nationalPokedexNumber;
    }

    public void setNationalPokedexNumber(int nationalPokedexNumber) {
        this.nationalPokedexNumber = nationalPokedexNumber;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Trade> getTradesWhereOffered() {
        return tradesWhereOffered;
    }

    public void setTradesWhereOffered(List<Trade> tradesWhereOffered) {
        this.tradesWhereOffered = tradesWhereOffered;
    }

    public List<Trade> getTradesWhereAccepted() {
        return tradesWhereAccepted;
    }

    public void setTradesWhereAccepted(List<Trade> tradesWhereAccepted) {
        this.tradesWhereAccepted = tradesWhereAccepted;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<User> getUsers2() {
        return users2;
    }

    public void setUsers2(List<User> users2) {
        this.users2 = users2;
    }
}
