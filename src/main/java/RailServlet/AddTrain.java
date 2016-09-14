package RailServlet;

import Railroad.TrainDao;
import Service.AdminBean;
import Service.MessageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author vbuevich
 *
 * Servlet class called from newStation.jsp by Employee in order to add new Train
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

        Boolean addTrain = TrainDao.addTrain(tNumber);
        if (addTrain) {
            message.setErrorMessage(null);
            message.setSuccessMessage("Train # " + trainNumber + " is succesfully added");
            bean.setTrainList(TrainDao.getTrainList());
        } else {
            message.setErrorMessage("Train is not added, please check and try again");
            message.setSuccessMessage(null);
        }

        response.sendRedirect(request.getContextPath() + "/newTrain.jsp");
    }
}