package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlet.LoginServlet;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AuthentificationAdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("DO FILTER");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // Get existant session or return null
        HttpSession session = req.getSession(false);

        if(session != null && session.getAttribute("userId") != null){
            System.out.println("ACCESS GRANTED");
            //Filtrage OK
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("ACCESS DENIED");
            resp.sendRedirect(LoginServlet.URL);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
