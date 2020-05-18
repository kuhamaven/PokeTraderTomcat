package austral.ing.lab1.service;

import austral.ing.lab1.model.Card;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cardmaker")
public class CollectionMakerServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String[] ids = gson.fromJson(req.getReader(),String[].class);
        for (int i = 0; i <ids.length ; i++) {
            System.out.println(ids[i]);
        }

    }

}

