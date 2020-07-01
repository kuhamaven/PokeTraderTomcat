package austral.ing.lab1.service;

import austral.ing.lab1.entity.Bids;
import austral.ing.lab1.entity.Trades;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Bid;
import austral.ing.lab1.model.Card;
import austral.ing.lab1.model.Trade;
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

@WebServlet("/hostverification")
public class HostVerificationServlet extends OptionsServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] tradeData = gson.fromJson(req.getReader(), String[].class);
        Optional<Trade> currentTrade = Trades.findById(Long.parseLong(tradeData[0]));
        currentTrade.get().setHostVerification(true);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        if (currentTrade.get().isBidderVerification()) {
            Optional<Bid> currentBid = Bids.findByTradeIdAndAccepted(Long.parseLong(tradeData[0]));
            Card bidderCard = currentBid.get().getCard();
            Card hostCard = currentTrade.get().getCard();
            User host = Users.findByEmail(currentTrade.get().getHostEmail()).get();
            User bidder = Users.findByEmail(currentBid.get().getBidderEmail()).get();
            host.addCard(bidderCard);
            host.removeCard(hostCard);
            bidder.addCard(hostCard);
            bidder.removeCard(bidderCard);
            Users.persist(host);
            Users.persist(bidder);

            out.print(gson.toJson("Verification Accepted-Trade Completed"));

        }

       else {
            out.print(gson.toJson("Verification Accepted"));

        }
        Trades.persist(currentTrade.get());
        resp.setStatus(200);
        out.close();

    }
}