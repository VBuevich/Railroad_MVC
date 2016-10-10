package Railroad;

import Service.AdminBean;
import Service.MessageBean;
import Service.ServiceBean;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.dao.StationDao;
import persistence.service.EmployeeService;
import persistence.service.PassengerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author vbuevich
 *
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);


    /**
     * Log-in page
     *
     * @return forward to login.jsp
     */
    @RequestMapping("/login")
    public String login() {

        return "login";
    }

    /**
     * Invoked from login.jsp in order to check user credentials
     *
     * @param request request
     * @param model model
     * @return forward to login page in case if login unsuccessful either to schedule / adminMenu
     */
    @RequestMapping("/loginCheck")
    public String loginCheck(HttpServletRequest request, Model model) {

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String status = request.getParameter("status");
        HttpSession session = request.getSession();

        if (email == null || email.equals("")) {
            model.addAttribute("errorMessage", "E-Mail is missing");
            return "login";
        }
        if (pass == null || pass.equals("")) {
            model.addAttribute("errorMessage", "Password is missing");
            return "login";
        }
        if (status == null || status.equals("")) {
            model.addAttribute("errorMessage", "Status is missing");
            return "login";
        } else if (status.equals("Passenger")) {
            session.setAttribute("bean", null);
            ServiceBean bean = ServiceBean.get(session);
            bean.setUser(PassengerService.checkPass(email, pass));
            if (bean.getUser() != null) {
                LOGGER.info("Passenger " + bean.getUser().getName() + " " + bean.getUser().getSurname() + " has successfully logged in");
                model.addAttribute("stationList", StationDao.getStationList());
                MessageBean.get(session); // Data Transfer Object (DTO) , used during ticketing operations in order to deliver messages to customer
                return "schedule";
            } else {
                LOGGER.info("Login error. Email: " + email + " password: " + pass + " status: " + status);
                session.setAttribute("bean", null);
                model.addAttribute("errorMessage", "E-mail or Password incorrect");
                return "login";
            }

        } else if (status.equals("Employee")) {
            session.setAttribute("bean", null);
            AdminBean bean = AdminBean.get(session);
            bean.setUser(EmployeeService.checkEmpl(email, pass));
            if (bean.getUser() != null) {
                LOGGER.info("Employee " + bean.getUser().getName() + " " + bean.getUser().getSurname() + " has successfully logged in");
                model.addAttribute("name", bean.getUser().getName());
                model.addAttribute("surname", bean.getUser().getSurname());

                return "adminMenu";
            } else {
                LOGGER.info("Login error. Email: " + email + " password: " + pass + " status: " + status);
                session.setAttribute("bean", null);
                model.addAttribute("errorMessage", "E-mail or Password incorrect");
                return "login";
            }
        } else {
            model.addAttribute("Internal error. Please try again.");
            return "login";
        }

    }
}
