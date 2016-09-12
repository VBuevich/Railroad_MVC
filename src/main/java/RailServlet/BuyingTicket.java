package RailServlet;

import Railroad.RailroadDao;
import Service.MessageBean;
import Service.ServiceBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by VBuevich on 08.09.2016.
 */
public class BuyingTicket extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServiceBean bean = ServiceBean.get(session);
        MessageBean message = MessageBean.get(session);

        String departureStation = request.getParameter("departureStation");
        String arrivalStation = request.getParameter("arrivalStation");
        String trainNumber = request.getParameter("trainNumber");

        int userId = bean.getUser().getPassengerId();
        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            message.setErrorMessage("Incorrect train number");
            response.sendRedirect(request.getContextPath() + "/findTrain.jsp");
            return;
        }

        String tryingToBuyTicket = RailroadDao.buyTicket(userId, departureStation, arrivalStation, tNumber);
        if (tryingToBuyTicket == null) {
            message.setErrorMessage(null);
            message.setSuccessMessage("You have successfully bought a ticket, train number " + trainNumber + " from " + departureStation + " to " + departureStation + "! Please check 'My tickets' to get a confirmation.");
        }
        else {
            message.setErrorMessage(tryingToBuyTicket);
            message.setSuccessMessage(null);
        }

        response.sendRedirect(request.getContextPath() + "/findTrain.jsp");
    }


}
