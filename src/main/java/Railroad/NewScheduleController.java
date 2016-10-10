package Railroad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.dao.ScheduleDao;
import persistence.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.Calendar;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewScheduleController {

    /**
     * Forwards to newSchedule.jsp
     *
     * @param model
     * @return forward to newSchedule.jsp
     */
    @RequestMapping("/newSchedule")
    public String newSchedule(Model model) {

        model.addAttribute("trainList", EmployeeService.getTrainList());
        model.addAttribute("stationList", EmployeeService.getStationList());
        return "newSchedule";
    }


    /**
     * Handles admins` request to add new Schedule and forwards back to newSchedule.jsp
     *
     * @param request
     * @param model
     * @return forward to newSchedule.jsp
     */
    @RequestMapping("/addSchedule")
    public String addSchedule(HttpServletRequest request, Model model) {

        String trainNumber = request.getParameter("trainNumber");
        String station = request.getParameter("station");
        String departureTime = request.getParameter("departureTime");

        int hour, minute, second = 0;

        try {
            String hourS = departureTime.substring(0, 2);
            String minuteS = departureTime.substring(3, 5);

            hour = Integer.parseInt(hourS);
            minute = Integer.parseInt(minuteS);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid time");
            return "newSchedule";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,1,1, hour, minute, second);
        Time dTime = new Time(calendar.getTimeInMillis());

        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid train number");
            return "newSchedule";
        }

        Boolean addSchedule = ScheduleDao.addSchedule(tNumber, station, dTime);

        if (addSchedule) {
            model.addAttribute("successMessage", "Schedule for train # " + trainNumber + " departing from " + station + " at " + departureTime + " is successfully added");
        } else {
            model.addAttribute("errorMessage", "Schedule for train # " + trainNumber + " departing from " + station + " at " + departureTime + " is not added, please check and try again");
        }

        model.addAttribute("trainList", EmployeeService.getTrainList());
        model.addAttribute("stationList", EmployeeService.getStationList());
        model.addAttribute("selectedStation", station);
        model.addAttribute("selectedTrain", trainNumber);
        return "newSchedule";
    }
}
