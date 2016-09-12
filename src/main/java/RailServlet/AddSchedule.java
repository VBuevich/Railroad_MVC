package RailServlet;

import Railroad.RailroadDao;
import Railroad.ScheduleDao;
import Railroad.StationDao;
import Service.AdminBean;
import Service.MessageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vbuevich on 13.09.2016.
 */
public class AddSchedule extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AdminBean bean = AdminBean.get(session);
        MessageBean message = MessageBean.get(session);
        String trainNumber = request.getParameter("trainNumber");
        String station = request.getParameter("station");
        String departureTime = request.getParameter("departureTime");

        int hour, minute, second = 0;

        try {
            String hourS = departureTime.substring(0, 2);
            String minuteS = departureTime.substring(3, 5);
            String secondS = departureTime.substring(6, 8);

            hour = Integer.parseInt(hourS);
            minute = Integer.parseInt(minuteS);
            second = Integer.parseInt(secondS);
        }
        catch (Exception e) {
            message.setErrorMessage("Invalid time");
            response.sendRedirect(request.getContextPath() + "/newSchedule.jsp");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,1,1, hour, minute, second);
        Time dTime = new Time(calendar.getTimeInMillis());

        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            message.setErrorMessage("Invalid train number");
            response.sendRedirect(request.getContextPath() + "/newSchedule.jsp");
            return;
        }

        String addSchedule = ScheduleDao.addSchedule(tNumber, station, dTime);

        if (addSchedule == null) {
            message.setErrorMessage(null);
            message.setSuccessMessage("Schedule for train # " + trainNumber + " departing from " + station + " at " + departureTime + " is succesfully added");
            bean.setStationList(RailroadDao.getStationList());
        } else {
            message.setSuccessMessage(null);
            message.setErrorMessage("Schedule is not added, please check and try again");
        }
        bean.setStationList(RailroadDao.getStationList());

        response.sendRedirect(request.getContextPath() + "/newSchedule.jsp");
    }
}

