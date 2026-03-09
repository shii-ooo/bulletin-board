package practice_webapp;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * Servlet Filter implementation class PracticeWebappFilter
 */
@WebFilter("/practice_webapp/admin/*")
public class PracticeWebappFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        if(session != null && session.getAttribute("UserId") != null) {
            session.setAttribute("login", true);
            session.setAttribute("id", session.getAttribute("UserId"));
        }
        
        chain.doFilter(request, response);
    }

}