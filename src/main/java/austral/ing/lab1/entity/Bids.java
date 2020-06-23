package austral.ing.lab1.entity;

import austral.ing.lab1.model.Bid;
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

public class Bids {

    public static Optional<Bid> findById(Long id){
        return tx(() ->
                Optional.ofNullable(currentEntityManager().find(Bid.class, id))
        );
    }

    public static List<Bid> listAllUserBids(String userId) {

        return tx(() -> LangUtils.<Bid>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Bid u  WHERE u.userId LIKE :userId")
                .setParameter("userId", userId).getResultList())

        );

    }


    public static List<Bid> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT u FROM Bid u").getResultList())
        );
    }

    public static Bid persist(Bid bid) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(bid);

            tx.commit();
            return bid;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


}
