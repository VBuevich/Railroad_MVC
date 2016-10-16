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
public class GoogleMapController {

    /**
     * forwards to googleMap.jsp
     *
     * @param model
     * @return forward to googleMap.jsp
     */
    @RequestMapping("/user/googleMap")
    public String googleMap(HttpServletRequest request, Model model) {

        return "googleMap";
    }

}