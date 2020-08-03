package austral.ing.lab1.util;

import austral.ing.lab1.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.json.JSONParser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Collectors;

import static austral.ing.lab1.util.LangUtils.EMPTY_ARRAY;
import static austral.ing.lab1.util.LangUtils.notNull;
import static java.lang.String.join;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
            try {

                final String tokenId = req.getParameterValues("tokenId")[0];

                String token = "{\"idToken\": \"" + tokenId + "\" }";

                HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(token);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://www.googleapis.com/identitytoolkit/v3/relyingparty/getAccountInfo?key=AIzaSyA9f6ahNiOBaLQHmydvcDgq0RyqKIZOyis"))
                        .POST(body).header("Content-Type", "application/json; charset=UTF-8").build();

                HttpClient client = HttpClient.newBuilder()
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .connectTimeout(Duration.ofSeconds(20))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if(response.statusCode()==200) {
                    String[] data = response.body().split("\"");
                    String userEmail="";
                    for (int i = 0; i <data.length ; i++) {
                        if(data[i].equals("email")){
                            userEmail=data[i+2];
                            break;
                        }

                    }
                    req.setAttribute("LoggedUser", userEmail);
                    chain.doFilter(req, res);
                }
                else
                {
                   // res.sendError(HttpServletResponse.SC_FORBIDDEN);
                   // return;
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    res.setHeader("Access-Control-Allow-Origin", "*");
                    res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
                    res.setStatus(308);
                    res.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                    res.setHeader("Location", "http://localhost:4200/login");
                    PrintWriter out = res.getWriter();

                    out.flush();
                }
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            }
        }
}
