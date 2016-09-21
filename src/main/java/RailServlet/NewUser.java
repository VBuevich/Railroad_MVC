package RailServlet;

import Persistence.Dao.StationDao;
import Persistence.Dao.TrainDao;
import Persistence.Service.EmployeeService;
import Persistence.Service.PassengerService;
import Service.MessageBean;
import Service.ServiceBean;
import Service.AdminBean;
import org.jboss.logging.Logger;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet class that checks User`s credentials
 *
 * @author vbuevich
 */
public class NewUser extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginCheck.class);

    /**
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { // changed from protected due to testing

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");
        String secret = request.getParameter("secret");

        HttpSession session = request.getSession();
        MessageBean message = MessageBean.get(session);

        if (name == null || name.equals("")) {
            message.setErrorMessage("Name is missing");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }
        if (surname == null || surname.equals("")) {
            message.setErrorMessage("Surname is missing");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }
        if (dob == null || dob.equals("")) {
            message.setErrorMessage("Date of birth is missing");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }
        if (email == null || email.equals("")) {
            message.setErrorMessage("E-mail is missing");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }
        if (pass1 == null || pass1.equals("") || pass2 == null || pass2.equals("")) {
            message.setErrorMessage("Password is missing");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }
        if (!pass1.equals(pass2)) {
            message.setErrorMessage("Passwords doesn`t match");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }
        if (secret == null || secret.equals("")) {
            message.setErrorMessage("Secret phrase is missing");
            response.sendRedirect(request.getContextPath() + "/newCustomer.jsp");
            return;
        }

        Boolean isSuccess = PassengerService.addUser(name, surname, dob, email, pass1, secret);
            if(isSuccess)
            {
                LOGGER.info("Passenger " + name + " " + surname + " has successfully registered as new user");
                message.setSuccessMessage("Registration success, please log-in");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            else
            {
                LOGGER.info("Registration error. Name: " + name + " surname: " + surname + " dob: " + dob + " email: " + email + " password: " + pass1 + " secret: " + secret);
                message.setErrorMessage("Registration error, please contact administrator");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }

    }

}