package practice_webapp;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class PracticeWebappLoginFilter
 */
@WebFilter("/practice_webapp/admin/create/*")
public class PracticeWebappLoginFilter extends HttpFilter implements Filter {
       
 private static final long serialVersionUID = 1L;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        if(session == null || session.getAttribute("UserId") == null) {
            ((HttpServletResponse)response).sendRedirect("../logout");
            return;
        }
        
        chain.doFilter(request, response);
    }

}