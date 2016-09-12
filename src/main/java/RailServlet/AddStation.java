package RailServlet;

import Railroad.RailroadDao;
import Railroad.StationDao;
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
public class AddStation extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AdminBean bean = AdminBean.get(session);
        MessageBean message = MessageBean.get(session);
        String stationName = request.getParameter("stationName");

        String addStation = StationDao.addStation(stationName);

        if (addStation == null) {
            message.setErrorMessage(null);
            message.setSuccessMessage("Station " + stationName + " is succesfully added");
            bean.setStationList(RailroadDao.getStationList());
        } else {
            message.setSuccessMessage(null);
            message.setErrorMessage("Station is not added, please check and try again");
        }
        bean.setStationList(RailroadDao.getStationList());

        response.sendRedirect(request.getContextPath() + "/newStation.jsp");
    }
}
