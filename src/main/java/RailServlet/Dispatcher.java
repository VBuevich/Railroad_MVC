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
 * Created by VBuevich on 11.09.2016.
 */
public class Dispatcher extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MessageBean message = MessageBean.get(session);
        Object bean = session.getAttribute("bean");

        if (session == null || bean == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // No logged-in user found, so redirect to login page.
        }

        String page = request.getParameter(request.getContextPath() + "page");

        message.setErrorMessage(null);
        message.setSuccessMessage(null);

        response.sendRedirect(page);
    }
}

