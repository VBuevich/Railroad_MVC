package RailServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Logoff extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        session.invalidate();
    }
}