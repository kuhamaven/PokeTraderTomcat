package austral.ing.lab1.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trade {

    @Column(name="Host_Email")
    private String hostEmail;

    @ManyToOne()
    @JsonManagedReference
    @JoinColumn(name = "ID")
    private Card card;

    @Id
    @Column(name="TID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "Opening_Date")
    private String openingDate;

    @Column(name = "Condition")
    private String condition;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    @JoinTable(
            name = "Requested_Cards",
            joinColumns = { @JoinColumn(name = "TID")},
            inverseJoinColumns = { @JoinColumn(name = "ID")}
    )
    private List<Card> willingToAccept = new ArrayList<>();

    @Column(name="Is_Open")
    private boolean isOpen=true;


    @Column(name="Host_Verification") //El que publico el trade confirma que se realizo de forma exitosa
    private boolean hostVerification;

    @Column(name="Bidder_Verification")//El que oferto por el trade confirma que se realizo de forma exitosa
    private boolean bidderVerification;

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Card> getWillingToAccept() {
        return willingToAccept;
    }

    public void setWillingToAccept(List<Card> cards) {
        this.willingToAccept = cards;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isHostVerification() {
        return hostVerification;
    }

    public void setHostVerification(boolean hostVerification) {
        this.hostVerification = hostVerification;
    }

    public boolean isBidderVerification() {
        return bidderVerification;
    }

    public void setBidderVerification(boolean bidderVerification) {
        this.bidderVerification = bidderVerification;
    }

}
