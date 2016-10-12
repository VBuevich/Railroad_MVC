package railroad.controller;

import railroad.service.ServiceBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;
import railroad.persistence.dao.TicketDao;
import railroad.persistence.entity.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class MyTicketsController {

    /**
     * Handles myTickets.jsp
     *
     * @param request
     * @param model
     * @return forward to myTickets.jsp
     */
    @RequestMapping("/myTickets")
    public String myTickets(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        ServiceBean bean = ServiceBean.get(session);

        List<Ticket> tickets = TicketDao.getTickets(bean.getUser().getPassengerId());

        if (tickets == null || tickets.size() == 0) {
            model.addAttribute("errorMessage", "Currently you have no tickets");
        } else {
            model.addAttribute("myTickets", tickets); // collection of passengers` tickets
        }


        model.addAttribute("stationList", StationDao.getStationList());
        return "myTickets";
    }

    /**
     * Handles passengers` log off request and forwards to login page
     *
     * @param request
     * @param model
     * @return forward to login.jsp
     */
    @RequestMapping("/logoff")
    public String logoff(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        session.invalidate();

        model.addAttribute("successMessage", "You have successfully logged off. Please come again!");
        return "login";
    }
}
