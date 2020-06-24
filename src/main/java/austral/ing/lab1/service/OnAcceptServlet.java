package austral.ing.lab1.service;

import austral.ing.lab1.entity.Bids;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Bid;
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

@WebServlet("/onaccept")
public class OnAcceptServlet extends OptionsServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] userData = gson.fromJson(req.getReader(),String[].class);
        Optional<User> currentUser = Users.findByEmail(userData[0]);
        Optional<Bid> currentBid = Bids.findById(Long.parseLong(userData[1]));
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        if(currentBid.get().getHostEmail().equals(currentUser.get().getEmail())){
            currentBid.get().setAccepted(true);
            Bids.persist(currentBid.get());
            List<Bid> otherBids = Bids.listAllTradeBids(currentBid.get().getTradeId());
            for (int i = 0; i <otherBids.size() ; i++) {
                if(otherBids.get(i)!=currentBid.get()){
                    otherBids.get(i).setRejected(true);
                    Bids.persist(otherBids.get(i));
                }
            }
            out.print(gson.toJson("Bid Accepted"));
            resp.setStatus(200);
            out.close();
        } else{
            out.print(gson.toJson("Unauthorized"));
            resp.setStatus(200);
            out.close(); }
    }

}