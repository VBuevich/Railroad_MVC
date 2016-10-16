package railroad.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author vbuevich
 *
 */
@Controller
public class LogoffController {


    /**
     * Handles passengers` log off request and forwards to login page
     *
     * @param request
     * @param model
     * @return forward to login.jsp
     */
    @RequestMapping("/logoff")
    public String logoff(HttpServletRequest request, HttpServletResponse response, Model model) {

        HttpSession session = request.getSession();
        session.invalidate();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        model.addAttribute("successMessage", "You have successfully logged off. Please come again!");
        return "login";
    }
}
