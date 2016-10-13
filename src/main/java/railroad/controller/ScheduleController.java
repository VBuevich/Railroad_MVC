package railroad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;
import railroad.persistence.dao.TrainDao;
import railroad.persistence.entity.Schedule;
import railroad.service.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String schedule(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        UserBean bean = UserBean.get(session); // session-scoped DTO
        if (!bean.getRole().equals("Passenger")) {
            model.addAttribute("errorMessage", "Please log-in as Passenger to access this page");
            return "login";
        }

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

        HttpSession session = request.getSession();
        UserBean bean = UserBean.get(session); // session-scoped DTO
        if (!bean.getRole().equals("Passenger")) {
            model.addAttribute("errorMessage", "Please log-in as Passenger to access this page");
            return "login";
        }

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
