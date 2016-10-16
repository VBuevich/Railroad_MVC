package railroad.controller;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;
import railroad.persistence.entity.User;
import railroad.service.EmployeeService;
import railroad.service.MessageBean;
import railroad.service.PassengerService;
import railroad.service.UserBean;

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
            UserBean bean = UserBean.get(session);
            // User p = PassengerService.checkPass(email, pass);
            User p = null;

            if (p != null) {
                LOGGER.info("Passenger " + p.getName() + " " + p.getSurname() + " has successfully logged in");
                bean.setName(p.getName());
                bean.setSurname(p.getSurname());
                bean.setUserId(p.getUserId());
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
            UserBean bean = UserBean.get(session);
            // User e = EmployeeService.checkEmpl(email, pass);
            User e = null;

            if (e != null) {
                LOGGER.info("Employee " + e.getName() + " " + e.getSurname() + " has successfully logged in");
                bean.setName(e.getName());
                bean.setSurname(e.getSurname());
                bean.setUserId(e.getUserId());

                model.addAttribute("name", e.getName());
                model.addAttribute("surname", e.getSurname());

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
