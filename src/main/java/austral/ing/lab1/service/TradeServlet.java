package austral.ing.lab1.service;

import austral.ing.lab1.entity.Cards;
import austral.ing.lab1.entity.Trades;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.Trade;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

@WebServlet("/createtrade")
public class TradeServlet extends OptionsServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] tradeData = gson.fromJson(req.getReader(), String[].class);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
            Trade trade = new Trade();
            trade.setHostId(Users.findByEmail(tradeData[0]).get().getId());
            trade.setCard(Cards.findById(tradeData[1]).get());
            trade.setCondition(tradeData[2]);
            int length=tradeData.length;
            List<Card> cards= new ArrayList<>((length-3));
        for (int i = 3; i <length ; i++) {
            cards.add(Cards.findById(tradeData[i]).get());
        }
           trade.setWillingToAccept(cards);
            Date date=Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            trade.setOpeningDate(dateFormat.format(date));
            Trades.persist(trade);

            out.print(gson.toJson("Trade Created"));
            resp.setStatus(201);

        out.close();
    }
}