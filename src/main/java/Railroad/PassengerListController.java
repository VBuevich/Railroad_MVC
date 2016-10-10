package Railroad;

import Service.PassengerList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class PassengerListController {

    /**
     * Forwards to passengerList.jsp
     *
     * @param model
     * @return forward to passengersList.jsp
     */
    @RequestMapping("/passengerList")
    public String passengerList(Model model) {

        model.addAttribute("trainList", EmployeeService.getTrainList());

        return "passengerList";
    }

    /**
     * Handles admin`s request of passengers` list for specific train
     *
     * @param model
     * @return forward back to passengersList.jsp
     */
    @RequestMapping("/getPassengerList")
    public String getPassengerList(HttpServletRequest request, Model model) {

        String trainNumber = request.getParameter("trainNumber");
        model.addAttribute("selectedTrain", trainNumber);
        model.addAttribute("trainList", EmployeeService.getTrainList());

        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Internal error: incorrect train number");
            return "passengerList";
        }

        List<PassengerList> passengerList = EmployeeService.getPassengerList(tNumber);
        if (passengerList == null) {
            model.addAttribute("errorMessage", "No passengers found for this train");
        } else {
            model.addAttribute("passengerList", passengerList);
        }

        return "passengerList";
    }
}
