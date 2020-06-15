package austral.ing.lab1.service;

import austral.ing.lab1.entity.Bids;
import austral.ing.lab1.entity.Cards;
import austral.ing.lab1.entity.Trades;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Bid;
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

@WebServlet("/createbid")
public class BidServlet extends OptionsServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] bidData = gson.fromJson(req.getReader(), String[].class);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        Bid bid = new Bid();
        bid.setUser(Users.findById(bidData[0]).get().getId());
        bid.setCard(Cards.findById(bidData[1]).get());
        bid.setTrade(Trades.findById(Long.parseLong(bidData[2])).get());
        Date date=Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        bid.setDate(dateFormat.format(date));
        Bids.persist(bid);

        out.print(gson.toJson("Bid Submitted"));
        resp.setStatus(201);

        out.close();
    }
}
