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
        String[] ids = gson.fromJson(req.getReader(),String[].class);
        Optional<User> currentUser = Users.findByEmail(ids[0]);
        System.out.println(currentUser.get().getEmail());
        for (int i = 1; i <ids.length ; i++) {
            Long id = Long.parseLong(ids[i]);
            currentUser.get().addCard(Cards.findById(id).get());
            System.out.println(ids[i]);
        }
        Users.persist(currentUser.get());
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        out.print("{}");
        resp.setStatus(200);
        out.close();
    }
}

