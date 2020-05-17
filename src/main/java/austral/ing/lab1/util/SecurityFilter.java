package austral.ing.lab1.util;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = "/*")
public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (isSecure(req)) {

            final String appId = getAppId(req);   //HAcerle llegar el Key y ID del post a firebase
            final String userToken = getUserToken(req);

            final String content = prepareBody(userToken);
            //Buscar cualquier libreria de HTTPClient Apache tiene
            final HttpResponse response = HttpClient.post(GOOGLE_URL + "?appId=" + appId, content)

            if (response.statusCode == 200) {
                req.setAttribute("LoggedUser",email); //Sacar del JSON
                //En un servlet hacemos que saque el LoggedUser del req y listo
                super.doFilter(req, resp, chain);
            }
            else {
                // Devolver un 403 (Unauthorized)
                // Copiarse del java security filter
            }


        }
    }

    private boolean isSecure(HttpServletRequest req) {
    }


}
