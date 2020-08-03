
package austral.ing.lab1.entity;

import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.Trade;
import austral.ing.lab1.util.LangUtils;

import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Trades {

    public static Optional<Trade> findById(Long id){
        return tx(() ->
                Optional.ofNullable(currentEntityManager().find(Trade.class, id))
        );
    }


    public static List<Trade> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT u FROM Trade u").getResultList())

        );
    }

    public static List<Trade> listAllHostTrades(String hostEmail) {

        return tx(() -> LangUtils.<Trade>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Trade u  WHERE u.hostEmail LIKE :hostEmail AND u NOT IN (SELECT u FROM Trade u WHERE u.hostVerification = true AND u.bidderVerification = true)")
                .setParameter("hostEmail", hostEmail).getResultList())

        );

    }

    public static List<Trade> exploreTrades(String hostEmail) {

        return tx(() -> LangUtils.<Trade>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Trade u  WHERE u.hostEmail NOT LIKE :hostEmail AND u.isOpen = true")
                .setParameter("hostEmail", hostEmail).getResultList())

        );

    }


    public static List<Trade> exploreTradesWithId(String hostEmail,String name,String supertype,String subtype,String rarity,String cardId) {

        return tx(() -> LangUtils.<Trade>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Trade u  WHERE u.hostEmail NOT LIKE :hostEmail AND  u.isOpen = true AND u.card.name LIKE : name AND u.card.supertype LIKE : supertype AND " +
                        "u.card.subtype LIKE : subtype AND u.card.rarity LIKE : rarity AND u.card.id LIKE : cardId")
                .setParameter("hostEmail", hostEmail).setParameter("name", name).setParameter("supertype", supertype)
                .setParameter("subtype", subtype).setParameter("rarity", rarity).setParameter("cardId", cardId).getResultList())

        );
    }

    public static List<Trade> exploreTrades(String hostEmail,String name,String supertype,String subtype,String rarity) {

        return tx(() -> LangUtils.<Trade>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Trade u  WHERE u.hostEmail NOT LIKE :hostEmail AND  u.isOpen = true AND u.card.name LIKE : name AND u.card.supertype LIKE : supertype AND " +
                        "u.card.subtype LIKE : subtype AND u.card.rarity LIKE : rarity")
                .setParameter("hostEmail", hostEmail).setParameter("name", name).setParameter("supertype", supertype)
                .setParameter("subtype", subtype).setParameter("rarity", rarity).getResultList())

        );
    }




    public static Trade persist(Trade card) {
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