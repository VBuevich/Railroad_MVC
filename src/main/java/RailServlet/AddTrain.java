package RailServlet;

import Railroad.RailroadDao;
import Railroad.StationDao;
import Railroad.TicketDao;
import Service.AdminBean;
import Service.MessageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by VBuevich on 12.09.2016.
 */
public class AddTrain extends HttpServlet {

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
            message.setErrorMessage("Invalid train number");
            response.sendRedirect(request.getContextPath() + "/newTrain.jsp");
            return;
        }

        String addTrain = TicketDao.addStation(tNumber);
        if (addTrain == null) {
            message.setErrorMessage(null);
            message.setSuccessMessage("Train # " + trainNumber + " is succesfully added");
            bean.setTrainList(RailroadDao.getTrainList());
        } else {
            message.setErrorMessage("Train is not added, please check and try again");
            message.setSuccessMessage(null);
        }
        bean.setStationList(RailroadDao.getStationList());

        response.sendRedirect(request.getContextPath() + "/newTrain.jsp");
    }
}