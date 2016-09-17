package RailServlet;

import Persistence.Dao.TicketDao;
import Persistence.Entity.Ticket;
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
 * Servlet class which is called by Dispatcher in case if Passenger wants to check his tickets
 * Redirects to myTickets.jsp
 *
 * @author vbuevich
 */
public class MyTickets extends HttpServlet {

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