package RailServlet;

import Railroad.*;
import Service.MessageBean;
import Service.ServiceBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by VBuevich on 08.09.2016.
 */
public class MyTickets extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServiceBean bean = ServiceBean.get(session);
        MessageBean message = MessageBean.get(session);

        List<Ticket> tickets = TicketDao.getTickets(bean.getUser().getPassengerId());

        if (tickets == null || tickets.size() == 0) {
            message.setErrorMessage("Currently you have no tickets");
            bean.setMyTickets(null);
        }
        else {
            message.setErrorMessage(null);
            bean.setMyTickets(tickets);
        }

        response.sendRedirect(request.getContextPath() + "/myTickets.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServiceBean bean = ServiceBean.get(session);
        MessageBean message = MessageBean.get(session);

        List<Ticket> tickets = TicketDao.getTickets(bean.getUser().getPassengerId());

        if (tickets == null || tickets.size() == 0) {
            message.setErrorMessage("Currently you have no tickets");
            bean.setMyTickets(null);
        }
        else {
            message.setErrorMessage(null);
            bean.setMyTickets(tickets);
        }

        response.sendRedirect(request.getContextPath() + "/myTickets.jsp");
    }


}