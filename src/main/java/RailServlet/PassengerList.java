package RailServlet;

import Railroad.DaoService;
import Railroad.Passenger;
import Railroad.RailroadDao;
import Service.AdminBean;
import Service.MessageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by vbuevich on 12.09.2016.
 */
public class PassengerList  extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AdminBean bean = AdminBean.get(session);
        MessageBean message = MessageBean.get(session);

        String trainNumber = request.getParameter("trainNumber");
        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            message.setErrorMessage("Internal error: incorrect train number");
            response.sendRedirect(request.getContextPath() + "/passengerList.jsp");
            return;
        }

        List<Passenger> passengerList = DaoService.getPassengerList(tNumber);
        if (passengerList == null) {
            bean.setPassengerList(null);
            message.setErrorMessage("No passengers found for this train");
        }
        else {
            bean.setPassengerList(passengerList);
            message.setErrorMessage(null);
        }

        response.sendRedirect(request.getContextPath() + "/passengerList.jsp");
    }
}
