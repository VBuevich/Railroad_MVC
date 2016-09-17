package RailServlet;

import Persistence.Service.PassengerService;
import Service.MessageBean;
import Service.ServiceBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author vbuevich
 *
 * Servlet class called from findTrain.jsp by Passenger in order to buy his chosen ticket
 */
public class BuyingTicket extends HttpServlet {

    /**
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
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

        message.setErrorMessage(null);
        message.setSuccessMessage(null);
        PassengerService.buyTicket(userId, departureStation, arrivalStation, tNumber, message);

        response.sendRedirect(request.getContextPath() + "/findTrain.jsp");
    }


}
