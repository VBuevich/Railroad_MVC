package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.dto.MessageBean;
import railroad.persistence.dao.ScheduleDao;
import railroad.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.Calendar;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewScheduleController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Forwards to newSchedule.jsp
     *
     * @param model
     * @return forward to newSchedule.jsp
     */
    @RequestMapping("/admin/newSchedule")
    public String newSchedule(Model model) {

        model.addAttribute("trainList", employeeService.getTrainList());
        model.addAttribute("stationList", employeeService.getStationList());
        return "newSchedule";
    }


    /**
     * Handles admins` request to add new Schedule and forwards back to newSchedule.jsp
     *
     * @param request
     * @param model
     * @return forward to newSchedule.jsp
     */
    @RequestMapping("/admin/addSchedule")
    public String addSchedule(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String trainNumber = request.getParameter("trainNumber");
        String station = request.getParameter("station");
        String departureTime = request.getParameter("departureTime");
        MessageBean message = MessageBean.get(session);

        message.setErrorMessage(null);
        Boolean addSchedule = employeeService.addSchedule(trainNumber, station, departureTime, message);

        if (addSchedule) {
            model.addAttribute("successMessage", "Schedule for train # " + trainNumber + " departing from " + station + " at " + departureTime + " is successfully added");
        } else {
            model.addAttribute("errorMessage", "Schedule for train # " + trainNumber + " departing from " + station + " at " + departureTime + " is not added, please check and try again. " + message.getErrorMessage());
        }

        model.addAttribute("trainList", employeeService.getTrainList());
        model.addAttribute("stationList", employeeService.getStationList());
        model.addAttribute("selectedStation", station);
        model.addAttribute("selectedTrain", trainNumber);
        return "newSchedule";
    }
}
