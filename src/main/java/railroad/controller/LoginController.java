package railroad.controller;

import railroad.persistence.entity.Employee;
import railroad.persistence.entity.Passenger;
import railroad.service.*;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;

import javax.mail.internet.AddressException;
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
            Passenger p = PassengerService.checkPass(email, pass);

            if (p != null) {
                LOGGER.info("Passenger " + p.getName() + " " + p.getSurname() + " has successfully logged in");
                bean.setName(p.getName());
                bean.setSurname(p.getSurname());
                bean.setUserId(p.getPassengerId());
                bean.setRole("Passenger");
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
            Employee e = EmployeeService.checkEmpl(email, pass);

            if (e != null) {
                LOGGER.info("Employee " + e.getName() + " " + e.getSurname() + " has successfully logged in");
                bean.setName(e.getName());
                bean.setSurname(e.getSurname());
                bean.setUserId(e.getEmployeeId());
                bean.setRole("Employee");

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
