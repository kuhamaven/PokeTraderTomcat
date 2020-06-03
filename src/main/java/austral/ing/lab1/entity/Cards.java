package austral.ing.lab1.entity;

import austral.ing.lab1.model.Card;
import austral.ing.lab1.util.LangUtils;

import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Cards {

    public static Optional<Card> findById(String id){
        return tx(() ->
                Optional.ofNullable(currentEntityManager().find(Card.class, id))
        );
    }

    public static Optional<Card> findByName(String name){
        return tx(() -> LangUtils.<Card>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Card u WHERE u.name LIKE :name")
                .setParameter("name", name).getResultList()).stream()
                .findFirst()
        );
    }

    public static List<Card> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT u FROM Card u").getResultList())
        );
    }

    public static Card persist(Card card) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(card);

            tx.commit();
            return card;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
