package Railroad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.dao.StationDao;
import persistence.dao.TrainDao;
import persistence.entity.Schedule;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class ScheduleController {

    /**
     * forwards to schedule.jsp
     *
     * @param model
     * @return forward to schedule.jsp
     */
    @RequestMapping("/schedule")
    public String schedule(Model model) {

        model.addAttribute("stationList", StationDao.getStationList());
        return "schedule";
    }

    /**
     * gets the schedule for single station for schedule.jsp
     *
     * @param request
     * @param model
     * @return forward to schedule.jsp
     */
    @RequestMapping("/getSchedule")
    public String getSchedule(HttpServletRequest request, Model model) {

        String stationName = request.getParameter("stationName");
        List<Schedule> scheduleList = TrainDao.trainList(stationName);

        if (scheduleList == null || scheduleList.size() == 0) {
            model.addAttribute("errorMessage", "Error: no departing trains for this station");
        } else {
            model.addAttribute("scheduleList", scheduleList);
        }

        model.addAttribute("selectedStation", stationName);
        model.addAttribute("stationList", StationDao.getStationList());
        return "schedule";
    }

}
