package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import railroad.persistence.dao.StationDao;
import railroad.dto.MessageBean;
import railroad.dto.Offer;
import railroad.service.PassengerService;
import railroad.dto.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class FindTrainController {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private PassengerService passengerService;

    /**
     * forwards for findTrain.jsp
     *
     * @param model
     * @return forward to findTrain.jsp
     */
    @RequestMapping("/user/findTrain")
    public String findTrain(HttpServletRequest request, Model model) {

        model.addAttribute("stationList", stationDao.getStationList());
        return "findTrain";
    }

    /**
     * Handles users` search of trains for his demands
     *
     * @param request
     * @param model
     * @return forwards back to findTrain.jsp
     */
    @RequestMapping("/user/ticketing")
    public String ticketing(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        UserBean bean = UserBean.get(session);

        String departureTime = request.getParameter("departureTime");
        String departureStation = request.getParameter("departureStation");
        String arrivalTime = request.getParameter("arrivalTime");
        String arrivalStation = request.getParameter("arrivalStation");

        List<Offer> offers = passengerService.getOffers(departureStation, departureTime, arrivalStation, arrivalTime);

        if (offers == null || offers.size() == 0) {
            model.addAttribute("errorMessage", "No offers found using your criteria of search");
        } else {
            model.addAttribute("offerList", offers);
            bean.setOfferList(offers);
        }

        model.addAttribute("departureTime", departureTime);
        model.addAttribute("selectedStationDeparture", departureStation);
        model.addAttribute("arrivalTime", arrivalTime);
        model.addAttribute("selectedStationArrival", arrivalStation);
        model.addAttribute("stationList", stationDao.getStationList());
        return "findTrain";
    }

    /**
     * AJAX request from findTrain.jsp
     *
     * @param request
     * @param model
     * @return XML containing seatmap for chosen train and occupied seats on it
     */
    @RequestMapping(value = "/user/occupiedSeats", method = RequestMethod.GET)
    @ResponseBody
    public String occupiedSeats(HttpServletRequest request, Model model) {

        String trainNumber = request.getParameter("trainNumber");
        StringBuilder sb = passengerService.getOccupiedSeats(trainNumber);

        return sb.toString();
    }

    /**
     * Handles passenger request of ticketing (pax decided to buy ticket)
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/user/buyTicket")
    public String buyTicket(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        UserBean bean = UserBean.get(session);
        MessageBean message = MessageBean.get(session);

        String departureStation = request.getParameter("departureStation");
        String arrivalStation = request.getParameter("arrivalStation");
        String trainNumber = request.getParameter("trainNumber");
        String selectedSeat = request.getParameter("selectedSeat");
        String departureTime = request.getParameter("departureTime");
        String arrivalTime = request.getParameter("arrivalTime");

        int userId = bean.getUserId();
        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        } catch (NumberFormatException e) {
            model.addAttribute("trainNumber", trainNumber);
            model.addAttribute("errorMessage", "Incorrect train number");
            model.addAttribute("departureTime", departureTime);
            model.addAttribute("selectedStationDeparture", departureStation);
            model.addAttribute("arrivalTime", arrivalTime);
            model.addAttribute("selectedStationArrival", arrivalStation);
            model.addAttribute("stationList", stationDao.getStationList());
            return "findTrain";
        }

        message.setSuccessMessage(null);
        message.setErrorMessage(null);

        passengerService.buyTicket(userId, departureStation, arrivalStation, tNumber, selectedSeat, message);

        if (message.getErrorMessage() != null) {
            model.addAttribute("errorMessage", message.getErrorMessage());
        }
        if (message.getSuccessMessage() != null) {
            model.addAttribute("successMessage", message.getSuccessMessage());
        }

        model.addAttribute("trainNumber", trainNumber);
        model.addAttribute("offerList", bean.getOfferList());
        model.addAttribute("departureTime", departureTime);
        model.addAttribute("selectedStationDeparture", departureStation);
        model.addAttribute("arrivalTime", arrivalTime);
        model.addAttribute("selectedStationArrival", arrivalStation);
        model.addAttribute("stationList", stationDao.getStationList());

        return "findTrain";
    }
}
