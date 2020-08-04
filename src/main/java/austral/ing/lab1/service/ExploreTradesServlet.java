
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
        List<Trade> filteredTrades = new ArrayList<>();
        User currentUser=Users.findByEmail(req.getAttribute("LoggedUser").toString()).get();
        //Wishlist
        if(data[5].equals("true")) {
            filteredTrades=FilterWithWishlist(data,currentUser);
        }
        else{
            filteredTrades=FilterWithoutWishlist(data,currentUser);

        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

        //final Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(filteredTrades); //gson.toJson(cards);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }


    private List<Trade> FilterWithWishlist(String[] data,User currentUser){
        List<Card> wishlist=currentUser.getWishlist();
        List<Trade> filteredTrades = new ArrayList<>();
        String name=data[0];
        String supertype=data[1];
        String type=data[2];
        String subtype=data[3];
        String rarity=data[4];
        for (int i = 0; i <wishlist.size(); i++) {
            Card c=wishlist.get(i);
            if(!type.equals("%") && c.getSupertype().charAt(0)!='P') continue;
            if(type.equals("%") || c.getTypes()[0].equals(type) || (c.getTypes().length>1 && c.getTypes()[1].equals(type))){
            filteredTrades.addAll(Trades.exploreTradesWithId(currentUser.getEmail(),name,supertype,subtype,rarity,c.getId()));
            }

        }
        return filteredTrades;
    }

    private List<Trade> FilterWithoutWishlist(String[] data,User currentUser){
        List<Trade> filteredTrades = new ArrayList<>();
        List<Trade> filteredTradesResult = new ArrayList<>();
        String name=data[0];
        String supertype=data[1];
        String type=data[2];
        String subtype=data[3];
        String rarity=data[4];
        filteredTrades.addAll(Trades.exploreTrades(currentUser.getEmail(),name,supertype,subtype,rarity));
        if(type.equals("%") ){
            return  filteredTrades;
        }
        for (int i = 0; i <filteredTrades.size() ; i++) {
            Trade t=filteredTrades.get(i);
            Card c=t.getCard();
            if(c.getSupertype().charAt(0)!='P') continue;
         if( c.getTypes()[0].equals(type) || (c.getTypes().length>1 && c.getTypes()[1].equals(type))){
             filteredTradesResult.add(t);
         }
        }
        return filteredTradesResult;
    }








}