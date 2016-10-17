package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;
import railroad.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewStationController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private StationDao stationDao;

    /**
     * Forwards to newStation.jsp
     *
     * @param model
     * @return forward to newStation.jsp
     */
    @RequestMapping("/admin/newStation")
    public String newStation(Model model) {

        model.addAttribute("stationList", employeeService.getStationList());

        return "newStation";
    }

    /**
     * Handles admins` request to add new Station and forwards back to newStation.jsp
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/admin/addStation")
    public String addStation(HttpServletRequest request, Model model) {

        String stationName = request.getParameter("stationName");

        if (stationName.length() < 2 || stationName.length() > 20) {

            model.addAttribute("errorMessage", "Station '" + stationName + "' is not added due to incorrect length, should be between 2 and 20");
            model.addAttribute("stationList", employeeService.getStationList());
            return "newStation";
        }
        Boolean addStation = stationDao.addStation(stationName);

        if (addStation) {
            model.addAttribute("successMessage", "Station '" + stationName + "' is successfully added");
        } else {
            model.addAttribute("errorMessage", "Station '" + stationName + "' is not added, please check and try again");
        }

        model.addAttribute("stationList", employeeService.getStationList());

        return "newStation";
    }
}
