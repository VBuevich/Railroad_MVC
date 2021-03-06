package railroad.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.service.PassengerService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vbuevich
 *
 */
@Controller
public class ForgotPasswordController {

    private static final Logger LOGGER = Logger.getLogger(ForgotPasswordController.class);

    @Autowired
    private PassengerService passengerService;

    /**
     * Forwards to forgotPassword.jsp
     *
     * @return forward to forgotPassword.jsp
     */
    @RequestMapping("/forgotPassword")
    public String passChange() {
        return "forgotPassword";
    }

    /**
     * Handles users` request of password change
     *
     * @param request
     * @param model
     * @return forwards to login.jsp page if successful either to passChange.jsp in case if user submitted wrong data
     */
    @RequestMapping("/passChange")
    public String passChange(HttpServletRequest request, Model model) {

        String email = request.getParameter("email");
        String secret = request.getParameter("secret");

        if (email == null || email.equals("")) {
            model.addAttribute("errorMessage", "E-Mail is missing");
            return "forgotPassword";
        }
        if (secret == null || secret.equals("")) {
            model.addAttribute("errorMessage", "Secret phrase is missing");
            return "forgotPassword";
        }
        else {
            Boolean isSuccess = passengerService.changePass(email, secret);
            if (isSuccess) {
                LOGGER.info("New password sent to " + email);
                model.addAttribute("successMessage", "Password changed, check your Email");
                return "login";
            } else {
                LOGGER.info("Secret phrase incorrect for " + email);
                model.addAttribute("errorMessage", "Secret phrase incorrect");
                return "forgotPassword";
            }
        }
    }
}
