package austral.ing.lab1.entity;

import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.User;
import austral.ing.lab1.util.LangUtils;


import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Users {

  public static Optional<User> findById(String id){
    return tx(() ->
      Optional.of(currentEntityManager().find(User.class, id))
    );
  }

  public static Optional<User> findByEmail(String email){
    return tx(() -> LangUtils.<User>checkedList(currentEntityManager()
        .createQuery("SELECT u FROM User u WHERE u.email LIKE :email")
        .setParameter("email", email).getResultList()).stream()
      .findFirst()
    );
  }

  public static List<User> listAll() {
    return tx(() ->
            checkedList(currentEntityManager().createQuery("SELECT u FROM User u").getResultList())
    );
  }

 /* public static List<User> listAllPendingRequests(String userId) {
    return tx(() ->
            checkedList(currentEntityManager().createNativeQuery("SELECT * FROM User u JOIN Pending_Requests p ON u.UID=p.Sender WHERE p.Receiver LIKE :userId").setParameter("userId",userId).getResultList())
    );
*/


  public static List<String> listAllReceivedRequests1(String userId) {
    return tx(() ->
            checkedList(currentEntityManager().createNativeQuery("SELECT p.Sender FROM  Pending_Requests p WHERE p.Receiver LIKE :userId").setParameter("userId",userId).getResultList())
    );


  }
  public static List<String> listAllPendingRequests1(String userId) {
    return tx(() ->
            checkedList(currentEntityManager().createNativeQuery("SELECT p.Receiver FROM  Pending_Requests p WHERE p.Sender LIKE :userId").setParameter("userId",userId).getResultList())
    );


  }


  public static List<User> listAllUsersById(List<String> list){
    List<User> result=new ArrayList<>();
    for (int i = 0; i <list.size() ; i++) {
      result.add(findById(list.get(i)).get());
    }

    return result;
  }

  public static List<User> listAllReceivedRequests(String userId){
    return listAllUsersById(listAllReceivedRequests1(userId));
  }
  public static List<User> listAllPendingRequests(String userId){
    return listAllUsersById(listAllPendingRequests1(userId));
  }




  public static User persist(User user) {
    final EntityTransaction tx = currentEntityManager().getTransaction();

    try {
      tx.begin();

      currentEntityManager().persist(user);

      tx.commit();
      return user;
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
  }
}
