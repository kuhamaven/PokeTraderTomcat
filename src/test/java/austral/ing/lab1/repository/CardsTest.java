package austral.ing.lab1.repository;

import austral.ing.lab1.entity.Cards;
import austral.ing.lab1.model.Card;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class CardsTest {
    private EntityManagerFactory managerFactory;

    @After
    public void tearDownCard() throws Exception {
        managerFactory.close();
    }

    @Before
    public void setUpCard() {
        managerFactory = Persistence.createEntityManagerFactory("testCard");
        EntityManagers.setFactory(managerFactory);
    }

    @Test
    public void createCard() {
        final Card card = new Card();

        card.setName("Pikachu");
        card.setImageURL("https://66.media.tumblr.com/3b75d664bcdc8b055a8064be5a651da9/tumblr_mqtyurBnJi1rxldc4o1_500.png");
        card.setType("Electric");
        card.setVariant("Basic");

        //assertThat(Cards.persist(card).getId(), greaterThan(0L));

        final Optional<Card> persistedCard = Cards.findById(card.getId());

        assertThat(persistedCard.isPresent(), is(true));
        assertThat(persistedCard.get().getImageURL(), is("https://66.media.tumblr.com/3b75d664bcdc8b055a8064be5a651da9/tumblr_mqtyurBnJi1rxldc4o1_500.png"));
        assertThat(persistedCard.get().getName(), is("Pikachu"));
        assertThat(persistedCard.get().getType(), is("Electric"));

        Optional<Card> byName = Cards.findByName(persistedCard.get().getName());
        System.out.println(byName);
    }

}