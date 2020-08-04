
package austral.ing.lab1.service;

import austral.ing.lab1.entity.Cards;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Card;
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

@WebServlet("/frr")
public class FriendRequestResponseServlet extends OptionsServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] frr = gson.fromJson(req.getReader(),String[].class);
        Optional<User> currentUser = Users.findByEmail(req.getAttribute("LoggedUser").toString());
        Optional<User> userThatRequested=Users.findByEmail(frr[0]);
        String response="";
        if(frr[1].equals("True")){
            currentUser.get().addToPending(userThatRequested.get());
            response="User Added to Friend List!";
        }else{
            userThatRequested.get().removeFromPending(currentUser.get());
            response="Friend Request Denied!";
        }
        Users.persist(userThatRequested.get());
        Users.persist(currentUser.get());
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        out.print(gson.toJson(response));
        resp.setStatus(200);
        out.close();
    }
}
