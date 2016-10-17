package railroad.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.dto.MessageBean;
import railroad.service.PassengerService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewCustomerController {

    private static final Logger LOGGER = Logger.getLogger(PassengerService.class);

    @Autowired
    private PassengerService passengerService;

    /**
     * Handles users` registration as new user
     *
     * @param request
     * @param model
     * @return forwards to login.jsp if user successfully registered as new user either newCustomer.jsp if passenger submitted wrong data
     */
    @RequestMapping("/newUser")
    public String newUser(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        MessageBean message = MessageBean.get(session);
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");
        String secret = request.getParameter("secret");

        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("dob", dob);
        model.addAttribute("email", email);
        model.addAttribute("pass1", pass1);
        model.addAttribute("pass2", pass2);
        model.addAttribute("secret", secret);

        message.setErrorMessage(null);
        Boolean isSuccess = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, true, message);
        if (isSuccess) {
            LOGGER.info("Passenger " + name + " " + surname + " has successfully registered as new user");
            model.addAttribute("successMessage", "Registration success, please log-in");
            return "login";
        }
        else if (!isSuccess && message.getErrorMessage() == null) { // errorMessage is null but method returns false means that something goes wrong
                                                                    // perhaps during DB access
            LOGGER.info("Registration error. Name: " + name + " surname: " + surname + " dob: " + dob + " email: " + email + " password: " + pass1 + " secret: " + secret);
            LOGGER.info("Registration error. Cause: Internal error");
            model.addAttribute("errorMessage", "Registration error, please contact administrator");
            return "newCustomer";
        }
        else
        {
            LOGGER.info("Registration error. Name: " + name + " surname: " + surname + " dob: " + dob + " email: " + email + " password: " + pass1 + " secret: " + secret);
            LOGGER.info("Registration error. Cause: " + message.getErrorMessage());
            model.addAttribute("errorMessage", message.getErrorMessage());
            return "newCustomer";
        }
    }



    /**
     * Forwards to newCustomer.jsp
     *
     * @return forward to newCustomer.jsp
     */
    @RequestMapping("/newCustomer")
    public String newCustomer() {
        return "newCustomer";
    }
}
