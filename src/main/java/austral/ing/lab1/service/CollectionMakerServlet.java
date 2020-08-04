package austral.ing.lab1.service;

import austral.ing.lab1.entity.Cards;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.User;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/cardmaker")
public class CollectionMakerServlet extends OptionsServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(req.getReader(),Card[].class);
        Optional<User> currentUser = Users.findByEmail(req.getAttribute("LoggedUser").toString());
        for (int i = 0; i <cards.length ; i++) {
            Card card=cards[i];
            if(Cards.findById(cards[i].getId()).isEmpty()){
                if(card.getSupertype().charAt(0)=='P'){
                    card.setSupertype("Pokemon");
                }
               Cards.persist(card);
            }
            currentUser.get().addCard(Cards.findById(cards[i].getId()).get());
        }
        Users.persist(currentUser.get());
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        out.print(gson.toJson("Cards added to collection!"));
        resp.setStatus(200);
        out.close();
    }
}

