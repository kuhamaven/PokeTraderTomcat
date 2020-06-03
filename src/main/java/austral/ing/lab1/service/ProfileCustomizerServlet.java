package austral.ing.lab1.service;

import austral.ing.lab1.entity.Users;
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

@WebServlet("/customize")
public class ProfileCustomizerServlet extends OptionsServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] userData = gson.fromJson(req.getReader(),String[].class);
        System.out.println(userData[0]);
        Optional<User> currentUser = Users.findByEmail(userData[0]);
        currentUser.get().setUserName(userData[1]);
        currentUser.get().setBio(userData[2]);
        currentUser.get().setPhotoUrl(userData[3]);

        Users.persist(currentUser.get());
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        out.print("Profile Updated");
        resp.setStatus(200);
        out.close();

    }

}