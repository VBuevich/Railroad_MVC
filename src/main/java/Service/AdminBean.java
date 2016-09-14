package Service;

import Railroad.Employee;
import Railroad.Passenger;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author VBuevich
 */
public class AdminBean {

    private Employee user;
    private List<String> stationList = null;
    private List<String> trainList = null;
    private List<Passenger> passengerList = null;

    public static AdminBean get(HttpSession session) {
        AdminBean bean = (AdminBean) session.getAttribute("bean");
        if (bean == null) {
            bean = new AdminBean();
            session.setAttribute("bean", bean);
        }
        return bean;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public List<String> getStationList() {
        return stationList;
    }

    public void setStationList(List<String> stationList) {
        this.stationList = stationList;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public List<String> getTrainList() {
        return trainList;
    }

    public void setTrainList(List<String> trainList) {
        this.trainList = trainList;
    }
}
