package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import railroad.persistence.dao.StationDao;
import railroad.persistence.dao.TicketDao;
import railroad.persistence.entity.Ticket;
import railroad.dto.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class MyTicketsController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private StationDao stationDao;


    /**
     * Handles myTickets.jsp
     *
     * @param request
     * @param model
     * @return forward to myTickets.jsp
     */
    @RequestMapping("/user/myTickets")
    public String myTickets(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        UserBean bean = UserBean.get(session);

        List<Ticket> tickets = ticketDao.getTickets(bean.getUserId());

        if (tickets == null || tickets.size() == 0) {
            model.addAttribute("errorMessage", "Currently you have no tickets");
        } else {
            model.addAttribute("myTickets", tickets); // collection of passengers` tickets
        }


        model.addAttribute("stationList", stationDao.getStationList());
        return "myTickets";
    }

}
