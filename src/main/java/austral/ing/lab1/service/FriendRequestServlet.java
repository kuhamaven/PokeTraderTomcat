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

@WebServlet("/friendrequest")
public class FriendRequestServlet extends OptionsServlet {
    //agregar devuelta usuarios al tomcat
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] userEmail= gson.fromJson(req.getReader(),String[].class);
        Optional<User> currentUser = Users.findByEmail(req.getAttribute("LoggedUser").toString());
        currentUser.get().addToPending(Users.findByEmail(userEmail[0]).get());
        Users.persist(currentUser.get());
        Users.persist(Users.findByEmail(userEmail[0]).get());
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        out.print(gson.toJson("Friend Request Sent!"));
        resp.setStatus(200);
        out.close();
    }
}
