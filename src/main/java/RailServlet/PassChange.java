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
public class PassChange extends HttpServlet {

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

        String email = request.getParameter("email");
        String secret = request.getParameter("secret");
        String status = request.getParameter("status");
        HttpSession session = request.getSession();
        MessageBean message = MessageBean.get(session);

        if (email == null || email.equals("")) {
            message.setErrorMessage("E-Mail is missing");
            response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp");
            return;
        }
        if (secret == null || secret.equals("")) {
            message.setErrorMessage("Secret phrase is missing");
            response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp");
            return;
        }

        if (status == null || status.equals("")) {
            message.setErrorMessage("Status is missing");
            response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp");
            return;
        }
        else if (status.equals("Passenger")) {
            Boolean isSuccess = PassengerService.changePass(email, secret);
            if(isSuccess)
            {
                LOGGER.info("New password sent to " + email);
                message.setSuccessMessage("Password changed, check your Email");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            else
            {
                LOGGER.info("Secret phrase incorrect for " + email);
                message.setErrorMessage("Secret phrase incorrect");
                response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp");
            }

        }
        else if (status.equals("Employee")) {
            Boolean isSuccess = EmployeeService.changePass(email, secret);
            if(isSuccess)
            {
                LOGGER.info("New password sent to " + email);
                message.setSuccessMessage("Password changed, check your Email");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            else
            {
                LOGGER.info("Secret phrase incorrect for " + email);
                message.setErrorMessage("Secret phrase incorrect");
                response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp");
            }


        }

    }

}