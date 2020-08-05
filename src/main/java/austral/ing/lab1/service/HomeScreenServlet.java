package austral.ing.lab1.service;

import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.Trade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/homescreen")
public class HomeScreenServlet extends OptionsServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Trade> trades = austral.ing.lab1.entity.Trades.listAll();
        List<Card> cards = new ArrayList<>();
        for (int i = trades.size()-1; i > 0; i--) {
            if(cards.size()>=5) {break;}
            cards.add(trades.get(i).getWillingToAccept().get(0));
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cards);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

}


