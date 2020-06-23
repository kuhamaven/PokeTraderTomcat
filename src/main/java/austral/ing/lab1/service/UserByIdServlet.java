package austral.ing.lab1.service;

import austral.ing.lab1.entity.Trades;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Trade;
import austral.ing.lab1.model.User;
import austral.ing.lab1.service.OptionsServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/userbyid")
public class UserByIdServlet extends OptionsServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] userId = gson.fromJson(req.getReader(), String[].class);
        User user = Users.findById((userId[0])).get();


        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

        //final Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user.getEmail()); //gson.toJson(cards);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}