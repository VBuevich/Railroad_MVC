package RailServlet;

import Persistence.Dao.ScheduleDao;
import Persistence.Dao.StationDao;
import Persistence.Service.PassengerService;
import Service.AdminBean;
import Service.MessageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Calendar;

/**
 * Created by VBuevich on 20.09.2016.
 */
public class OccupiedSeats extends HttpServlet {

    /**
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String trainNumber = request.getParameter("trainNumber");

        StringBuilder sb = PassengerService.getOccupiedSeats(trainNumber);

        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.append(sb.toString());


    }
}