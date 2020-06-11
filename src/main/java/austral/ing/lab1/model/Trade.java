package austral.ing.lab1.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trade {
    @ManyToOne()
    @JoinColumn(name = "UID")// esta sentencia vincula al trade con su host por la columna UID
                            //si bien host es un user no tengo atodo el usuario adentro
    private User host;

    @ManyToOne()
    @JoinColumn(name = "ID")
    private Card card;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "Opening_Date")
    private String openingDate;

    @Column(name = "Condition")
    private String condition;

    @ManyToMany(mappedBy = "Willing_to_Accept") //cartas que el host acepta
    @JsonBackReference
    private List<Card> cards = new ArrayList<>();

    @Column(name="Is_Open")
    private boolean isOpen=true;

    @OneToMany(mappedBy = "ID", cascade = CascadeType.ALL)
    private List<Bid> bidders;

    @Column(name="Host_Verification") //El que publico el trade confirma que se realizo de forma exitosa
    private boolean hostVerification;

    @Column(name="Bidder_Verification")//El que oferto por el trade confirma que se realizo de forma exitosa
    private boolean bidderVerification;

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public List<Bid> getBidders() {
        return bidders;
    }

    public void setBidders(List<Bid> bidders) {
        this.bidders = bidders;
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
