package Railroad;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.service.PassengerService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vbuevich
 *
 */
@Controller
public class NewCustomerController {

    private static final Logger LOGGER = Logger.getLogger(PassengerService.class);

    /**
     * Handles users` registration as new user
     *
     * @param request
     * @param model
     * @return forwards to login.jsp if user successfully registered as new user either newCustomer.jsp if passenger submitted wrong data
     */
    @RequestMapping("/newUser")
    public String newUser(HttpServletRequest request, Model model) {

        Pattern pName = Pattern.compile("^[A-z]{2,15}$");
        Pattern pDate = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");

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

        if (name == null || name.equals("")) {
            model.addAttribute("errorMessage", "Name is missing");
            return "newCustomer";
        }
        else {
            Matcher m = pName.matcher(name);
            if (!m.matches()) {
                model.addAttribute("errorMessage", "Please check name");
                return "newCustomer";
            }
        }
        if (surname == null || surname.equals("")) {
            model.addAttribute("errorMessage", "Surname is missing");
            return "newCustomer";

        }
        else {
            Matcher m = pName.matcher(surname);
            if (!m.matches()) {
                model.addAttribute("errorMessage", "Please check surname");
                return "newCustomer";
            }
        }
        if (dob == null || dob.equals("")) {
            model.addAttribute("errorMessage", "Date of birth is missing");
            return "newCustomer";
        }
        else {
            Matcher m = pDate.matcher(dob);
            if (!m.matches()) {
                model.addAttribute("errorMessage", "Please check date of birth");
                return "newCustomer";
            }
        }
        if (email == null || email.equals("")) {
            model.addAttribute("errorMessage", "E-mail is missing");
            return "newCustomer";
        }
        else {
            try {
                InternetAddress validEmail = new InternetAddress(email);
                validEmail.validate();
            }
            catch (AddressException e) {
                model.addAttribute("errorMessage", "E-mail is incorrect");
                return "newCustomer";
            }
        }
        if (pass1 == null || pass1.equals("") || pass2 == null || pass2.equals("")) {
            model.addAttribute("errorMessage", "Password is missing");
            return "newCustomer";
        }
        if (!pass1.equals(pass2)) {
            model.addAttribute("errorMessage", "Passwords does not match");
            return "newCustomer";
        }
        if (secret == null || secret.equals("")) {
            model.addAttribute("errorMessage", "Secret phrase is missing");
            return "newCustomer";
        }

        Boolean isSuccess = PassengerService.addUser(name, surname, dob, email, pass1, secret);
        if (isSuccess) {
            LOGGER.info("Passenger " + name + " " + surname + " has successfully registered as new user");
            model.addAttribute("successMessage", "Registration success, please log-in");
            return "login";
        } else {
            LOGGER.info("Registration error. Name: " + name + " surname: " + surname + " dob: " + dob + " email: " + email + " password: " + pass1 + " secret: " + secret);
            model.addAttribute("errorMessage", "Registration error, please contact administrator");
            return "login";
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
