package railroad.controller;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.Main;
import railroad.service.EmployeeService;
import railroad.service.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewTrainController {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    /**
     * Forwards to newTrain.jsp
     *
     * @param model
     * @return forward to newStation.jsp
     */
    @RequestMapping("/admin/newTrain")
    public String newTrain(HttpServletRequest request, Model model) {

        model.addAttribute("trainList", EmployeeService.getTrainList());
        model.addAttribute("templateNames", EmployeeService.getTemplateNames());

        return "newTrain";
    }

    /**
     * Handles admins` request to add new Train and forwards back to newTrain.jsp
     *
     * @param request
     * @param model
     * @return forward to newTrain.jsp
     */
    @RequestMapping("/admin/addTrain")
    public String addTrain(HttpServletRequest request, Model model) {

        String trainNumber = request.getParameter("trainNumber");
        String templateId = request.getParameter("templateId");

        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid train number");
            model.addAttribute("templateNames", EmployeeService.getTemplateNames());
            model.addAttribute("trainList", EmployeeService.getTrainList());
            return "newTrain";
        }

        Boolean addTrain = false;
        try {
            addTrain = EmployeeService.addTrain(tNumber, templateId);
        }
        catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        if (addTrain) {
            model.addAttribute("successMessage", "Train # " + trainNumber + " is succesfully added");
        } else {
            model.addAttribute("errorMessage", "Train # " + trainNumber + " is not added, please check and try again");
        }

        model.addAttribute("templateNames", EmployeeService.getTemplateNames());
        model.addAttribute("trainList", EmployeeService.getTrainList());
        return "newTrain";
    }
}
