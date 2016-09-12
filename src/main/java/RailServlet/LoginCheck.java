package RailServlet;

import Railroad.RailroadDao;
import Service.MessageBean;
import Service.ServiceBean;
import Service.AdminBean;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginCheck extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            bean.setUser(RailroadDao.checkPass(email, pass));
            if(bean.getUser() != null)
            {
                bean.setStationList(RailroadDao.getStationList());
                response.sendRedirect(request.getContextPath() + "/schedule.jsp");
            }
            else
            {
                session.setAttribute("bean", null);
                message.setErrorMessage("E-mail or Password incorrect");
                RequestDispatcher rs = request.getRequestDispatcher("/login.jsp");
                rs.include(request, response);
            }

        }
        else if (status.equals("Employee")) {
            session.setAttribute("bean", null);
            AdminBean bean = AdminBean.get(session);
            bean.setUser(RailroadDao.checkEmpl(email, pass));
            if(bean.getUser() != null)
            {
                bean.setStationList(RailroadDao.getStationList());
                bean.setTrainList(RailroadDao.getTrainList());
                response.sendRedirect(request.getContextPath() + "/adminMenu.jsp");
            }
            else
            {
                session.setAttribute("bean", null);
                message.setErrorMessage("E-mail or Password incorrect");
                RequestDispatcher rs = request.getRequestDispatcher("/login.jsp");
                rs.include(request, response);
            }


        }

    }

}