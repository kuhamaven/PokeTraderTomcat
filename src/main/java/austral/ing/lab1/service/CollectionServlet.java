package austral.ing.lab1.service;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/collection")
public class CollectionServlet extends OptionsServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] ids = gson.fromJson(req.getReader(),String[].class);
        System.out.println(ids[0]);
        Optional<User> currentUser = Users.findByEmail(ids[0]);
        final List<Card> cards = currentUser.get().getCards();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cards);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

}


