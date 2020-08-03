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
//arregar con nueva logica donde hay que arreglar cartas
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Card[] tradeData = gson.fromJson(req.getReader(), Card[].class);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
            Trade trade = new Trade();
            trade.setHostEmail(req.getAttribute("LoggedUser").toString());
            trade.setCard(Cards.findById(req.getParameterValues("hostcardid")[0]).get());
            trade.setCondition(req.getParameterValues("condition")[0]);
            int length=tradeData.length;
            List<Card> cards= new ArrayList<>((length));
        for (int i = 0; i <length ; i++) {
             Card card=tradeData[i];
            if(Cards.findById(card.getId()).isEmpty()){
                Cards.persist(card);
            }
            cards.add(card);

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