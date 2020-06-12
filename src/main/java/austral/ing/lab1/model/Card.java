package austral.ing.lab1.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card {

    @Column(name = "Name")
    private String name;

    @Column(name = "Image_Url")
    private String imageURL;

    @Id
    private String id;

    @Column(name = "Type")
    private String type;

    @Column(name = "Variant")
    private String variant;

    @ManyToMany(mappedBy = "cards")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    private List<Trade> tradesWhereOffered;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
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
}
