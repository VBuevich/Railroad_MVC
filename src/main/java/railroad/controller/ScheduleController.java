package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;
import railroad.persistence.dao.TrainDao;
import railroad.persistence.entity.Schedule;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class ScheduleController {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private TrainDao trainDao;

    /**
     * forwards to schedule.jsp
     *
     * @param model
     * @return forward to schedule.jsp
     */
    @RequestMapping("/user/schedule")
    public String schedule(HttpServletRequest request, Model model) {

        model.addAttribute("stationList", stationDao.getStationList());
        return "schedule";
    }

    /**
     * gets the schedule for single station for schedule.jsp
     *
     * @param request
     * @param model
     * @return forward to schedule.jsp
     */
    @RequestMapping("/user/getSchedule")
    public String getSchedule(HttpServletRequest request, Model model) {

        String stationName = request.getParameter("stationName");
        List<Schedule> scheduleList = trainDao.trainList(stationName);

        if (scheduleList == null || scheduleList.size() == 0) {
            model.addAttribute("errorMessage", "Error: no departing trains for this station");
        } else {
            model.addAttribute("scheduleList", scheduleList);
        }

        model.addAttribute("selectedStation", stationName);
        model.addAttribute("stationList", stationDao.getStationList());
        return "schedule";
    }

}
