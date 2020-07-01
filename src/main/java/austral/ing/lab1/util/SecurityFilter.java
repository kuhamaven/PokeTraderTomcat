package austral.ing.lab1.util;

import com.google.gson.Gson;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.net.Authenticator;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.http.HttpResponse;
import java.time.Duration;

import static austral.ing.lab1.util.LangUtils.EMPTY_ARRAY;
import static austral.ing.lab1.util.LangUtils.notNull;
import static java.lang.String.join;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (isSecure(req)) {
            try {

                final String tokenId = req.getParameterValues("tokenId")[0];

                String token = "{\"idToken\": \"" + tokenId + "\" }";

                HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(token);
                System.out.println(token);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://www.googleapis.com/identitytoolkit/v3/relyingparty/getAccountInfo?key=AIzaSyA9f6ahNiOBaLQHmydvcDgq0RyqKIZOyis"))
                        .POST(body).header("Content-Type", "application/json; charset=UTF-8").build();

                HttpClient client = HttpClient.newBuilder()
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .connectTimeout(Duration.ofSeconds(20))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());

                if(response.statusCode()==200){
                    //sacar email del user del response.body
                    System.out.println("entre al if");
                    //req.setAttribute("LoggedUser",email);
                    chain.doFilter(req,res);


                }else{
                    res.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;



                }
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            }



       /*     final String content = prepareBody(userToken);
            //Buscar cualquier libreria de HTTPClient Apache tiene
            final HttpResponse response = HttpClient.post(GOOGLE_URL + "?appId=" + appId, content)

            if (response.statusCode == 200) {
                req.setAttribute("LoggedUser",email); //Sacar del JSON
                //En un servlet hacemos que saque el LoggedUser del req y listo*/

        }
    }






    private boolean isSecure(HttpServletRequest req) {
            return true;
        }



}
