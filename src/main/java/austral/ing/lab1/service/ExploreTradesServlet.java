
package austral.ing.lab1.service;

import austral.ing.lab1.entity.Bids;
import austral.ing.lab1.entity.Cards;
import austral.ing.lab1.entity.Trades;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Bid;
import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.Trade;
import austral.ing.lab1.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet("/exploretrades")
public class ExploreTradesServlet extends OptionsServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] data = gson.fromJson(req.getReader(),String[].class);
        List<Trade> trades = new ArrayList<>();
        String mail = data[0];
        if(!data[1].equals("All Types") && !data[2].equals("All Variants")) {
            trades = Trades.exploreTradesWithVariantAndTypeFilter(mail, data[1], data[2]);
        }
        else{
            if(!data[2].equals("All Variants")){
                trades=Trades.exploreTradesWithVariantFilter(mail,data[2]);
            }
            else if(!data[1].equals("All Types")){
                trades=Trades.exploreTradesWithTypeFilter(mail,data[1]);
            }
      else trades=Trades.exploreTrades(mail);
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

        //final Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(trades); //gson.toJson(cards);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }


}