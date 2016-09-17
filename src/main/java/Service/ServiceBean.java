package Service;

import Persistence.Entity.Passenger;
import Persistence.Entity.Schedule;
import Persistence.Entity.Ticket;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author VBuevich
 */
public class ServiceBean {
    private Passenger user = null;
    private List<String> stationList = null;
    private List<Schedule> scheduleList = null;
    private List<Offer> offerList = null;
    private List<Ticket> myTickets = null;

    public static ServiceBean get(HttpSession session) {
        ServiceBean bean = (ServiceBean)session.getAttribute("bean");
        if (bean == null) {
            bean = new ServiceBean();
            session.setAttribute("bean", bean);
        }
        return bean;
    }

    public Passenger getUser() {
        return user;
    }

    public void setUser(Passenger user) {
        this.user = user;
    }

    public List<String> getStationList() {
        return stationList;
    }

    public void setStationList(List<String> stationList) {
        this.stationList = stationList;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public List<Ticket> getMyTickets() {
        return myTickets;
    }

    public void setMyTickets(List<Ticket> myTickets) {
        this.myTickets = myTickets;
    }
}
