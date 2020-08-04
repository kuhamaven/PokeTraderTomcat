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

@WebServlet("/profile")
public class ProfileServlet extends OptionsServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] ids = gson.fromJson(req.getReader(),String[].class);
        Optional<User> currentUser = Users.findByEmail(ids[0]); //Como este busca perfiles, no es siempre el logeado
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        PrintWriter out = resp.getWriter();
        if(!currentUser.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(currentUser.get());
            currentUser.get().setRecentlyModified(false);
            currentUser.get().setRecentlyModifiedWishlist(false);
            Users.persist(currentUser.get());
            out.print(json);
            resp.setStatus(201);
        }
        else{
            ObjectMapper objectMapper = new ObjectMapper();
            User user= new User();
            user.setEmail("null field");
            String json = objectMapper.writeValueAsString(user);
            out.print(json);
            resp.setStatus(201);
        }

        out.flush();
    }

}
