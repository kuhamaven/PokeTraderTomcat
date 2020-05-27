package austral.ing.lab1.service;

import austral.ing.lab1.model.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/card")
public class CardServlet extends OptionsServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Card> cards = austral.ing.lab1.entity.Cards.listAll();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

        //final Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cards); //gson.toJson(cards);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

}


