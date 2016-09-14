package RailServlet;

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
 * @author vbuevich
 *
 * Servlet class called from newStation.jsp by Employee in order to add new Station
 */
public class AddStation extends HttpServlet {

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
        AdminBean bean = AdminBean.get(session);
        MessageBean message = MessageBean.get(session);
        String stationName = request.getParameter("stationName");

        Boolean addStation = StationDao.addStation(stationName);

        if (addStation) {
            message.setErrorMessage(null);
            message.setSuccessMessage("Station " + stationName + " is succesfully added");
            bean.setStationList(StationDao.getStationList());
        } else {
            message.setSuccessMessage(null);
            message.setErrorMessage("Station is not added, please check and try again");
        }
        bean.setStationList(StationDao.getStationList());

        response.sendRedirect(request.getContextPath() + "/newStation.jsp");
    }
}
