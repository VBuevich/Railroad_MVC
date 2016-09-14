package RailServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet class which is called from many jsp`s navbars in order to end users activity and log off
 * redirects to login page
 *
 * @author vbuevich
 */
public class Logoff extends HttpServlet {

    /**
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        session.invalidate();
    }
}