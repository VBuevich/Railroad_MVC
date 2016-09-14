package RailServlet;

import Railroad.*;
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
public class LoginCheck extends HttpServlet {

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
        String pass = request.getParameter("pass");
        String status = request.getParameter("status");
        HttpSession session = request.getSession();
        MessageBean message = MessageBean.get(session);

        if (email == null || email.equals("")) {
            message.setErrorMessage("E-Mail is missing");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        if (pass == null || pass.equals("")) {
            message.setErrorMessage("Password is missing");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (status == null || status.equals("")) {
            message.setErrorMessage("Status is missing");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        else if (status.equals("Passenger")) {
            session.setAttribute("bean", null);
            ServiceBean bean = ServiceBean.get(session);
            bean.setUser(PassengerService.checkPass(email, pass));
            if(bean.getUser() != null)
            {
                LOGGER.info("Passenger " + bean.getUser().getName() + " " + bean.getUser().getSurname() + " has successfully logged in");
                bean.setStationList(StationDao.getStationList());
                response.sendRedirect(request.getContextPath() + "/schedule.jsp");
            }
            else
            {
                LOGGER.info("Login error. Email: " + email + " password: " + pass + " status: " + status);
                session.setAttribute("bean", null);
                message.setErrorMessage("E-mail or Password incorrect");
                RequestDispatcher rs = request.getRequestDispatcher("/login.jsp");
                rs.include(request, response);
            }

        }
        else if (status.equals("Employee")) {
            session.setAttribute("bean", null);
            AdminBean bean = AdminBean.get(session);
            bean.setUser(EmployeeService.checkEmpl(email, pass));
            if(bean.getUser() != null)
            {
                LOGGER.info("Employee " + bean.getUser().getName() + " " + bean.getUser().getSurname() + " has successfully logged in");
                bean.setStationList(StationDao.getStationList());
                bean.setTrainList(TrainDao.getTrainList());
                response.sendRedirect(request.getContextPath() + "/adminMenu.jsp");
            }
            else
            {
                LOGGER.info("Login error. Email: " + email + " password: " + pass + " status: " + status);
                session.setAttribute("bean", null);
                message.setErrorMessage("E-mail or Password incorrect");
                RequestDispatcher rs = request.getRequestDispatcher("/login.jsp");
                rs.include(request, response);
            }


        }

    }

}