package railroad.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewTrainController {

    private static final Logger LOGGER = Logger.getLogger(NewTrainController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Forwards to newTrain.jsp
     *
     * @param model
     * @return forward to newStation.jsp
     */
    @RequestMapping("/admin/newTrain")
    public String newTrain(HttpServletRequest request, Model model) {

        model.addAttribute("trainList", employeeService.getTrainList());
        model.addAttribute("templateNames", employeeService.getTemplateNames());

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
            if (tNumber < 1) {
                throw new NumberFormatException(); // train numbers should be positive so < 1 we count as error
            }
        }
        catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid train number");
            model.addAttribute("templateNames", employeeService.getTemplateNames());
            model.addAttribute("trainList", employeeService.getTrainList());
            return "newTrain";
        }

        Boolean addTrain = false;
        try {
            addTrain = employeeService.addTrain(tNumber, templateId);
        }
        catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        if (addTrain) {
            model.addAttribute("successMessage", "Train # " + trainNumber + " is succesfully added");
        } else {
            model.addAttribute("errorMessage", "Train # " + trainNumber + " is not added, please check and try again");
        }

        model.addAttribute("templateNames", employeeService.getTemplateNames());
        model.addAttribute("trainList", employeeService.getTrainList());
        return "newTrain";
    }
}
