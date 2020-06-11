
package austral.ing.lab1.entity;

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

    public static Optional<Trade> findById(String id){
        return tx(() ->
                Optional.ofNullable(currentEntityManager().find(Trade.class, id))
        );
    }


    public static List<Trade> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT u FROM Card u").getResultList())
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