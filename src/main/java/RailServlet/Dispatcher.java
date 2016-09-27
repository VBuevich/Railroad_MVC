package RailServlet;

import Service.MessageBean;
import Service.ServiceBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class "dispatcher" that is assuring that user is logged in ,
 * managing informative messages and redirects users to chosen page
 * Is called from many jsp`s from navbar tab
 *
 * @author vbuevich
 */
public class Dispatcher extends HttpServlet {

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
        MessageBean message = MessageBean.get(session);
        Object bean = session.getAttribute("bean");

        if (session == null || bean == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // No logged-in user found, so redirect to login page.
        }

        String page = request.getParameter("page");

        message.setErrorMessage(null);
        message.setSuccessMessage(null);

        if (page.equals("/myTickets.jsp")) {
            response.sendRedirect(request.getContextPath() + "/RailServlet/myTickets");
            return;
        }

        response.sendRedirect(request.getContextPath() + page);
    }
}

