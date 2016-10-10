package Railroad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.dao.StationDao;
import persistence.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewStationController {

    /**
     * Forwards to newStation.jsp
     *
     * @param model
     * @return forward to newStation.jsp
     */
    @RequestMapping("/newStation")
    public String newStation(Model model) {

        model.addAttribute("stationList", EmployeeService.getStationList());

        return "newStation";
    }

    /**
     * Handles admins` request to add new Station and forwards back to newStation.jsp
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/addStation")
    public String addStation(HttpServletRequest request, Model model) {

        String stationName = request.getParameter("stationName");

        Boolean addStation = StationDao.addStation(stationName);

        if (addStation) {
            model.addAttribute("successMessage", "Station '" + stationName + "' is succesfully added");
        } else {
            model.addAttribute("errorMessage", "Station '" + stationName + "' is not added, please check and try again");
        }

        model.addAttribute("stationList", EmployeeService.getStationList());

        return "newStation";
    }
}
