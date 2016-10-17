package railroad.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import railroad.dto.MessageBean;
import railroad.dto.PassengerList;
import railroad.persistence.dao.*;
import railroad.persistence.entity.Ticket;
import railroad.persistence.entity.UserDetails;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author vbuevich
 *
 * railroad.service class for DAO requests of Employee activity
 */
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);
    private static Mailer mailer = new Mailer();


    /**
     * Method that adds new schedule
     *
     * @param trainNumber Train number
     * @param station Station
     * @param departureTime The time of departure of train
     * @return true if schedule is successfully added , false if fail
     */
    public static Boolean addSchedule(String trainNumber, String station, String departureTime, MessageBean message) {

        int hour, minute, second = 0;

        try {
            String hourS = departureTime.substring(0, 2);
            String minuteS = departureTime.substring(3, 5);

            hour = Integer.parseInt(hourS);
            minute = Integer.parseInt(minuteS);
        }
        catch (Exception e) {
            message.setErrorMessage("Invalid time");
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,1,1, hour, minute, second);
        Time dTime = new Time(calendar.getTimeInMillis());

        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            message.setErrorMessage("Invalid train number");
            return false;
        }
        return ScheduleDao.addSchedule(tNumber, station, dTime);
    }

    /**
     * Method adds new Train and new Seatmap for it
     *
     * @param trainNumber Train number
     * @return true if Train and Seatmap were successfully added, false in case of error
     */
    public static Boolean addTrain(int trainNumber, String templateId) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;
        Boolean isSuccess = false;

        try {
            tx = session.beginTransaction(); // we are doing transaction due to the fact that we are wrighting to different tables

            Boolean addTrain =  TrainDao.addTrain(trainNumber, templateId, session); // adding Traing
            Boolean addSeatmap = SeatmapDao.createSeatmap(trainNumber, templateId, session); // adding Seatmap

            if (addTrain && addSeatmap) { // if both success
                tx.commit(); // successful commit
                isSuccess = true; // so we return true - success
            }
            else { // if we have just partial success - we doing rollback
                if (tx != null) tx.rollback();
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (tx != null) tx.rollback();
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return isSuccess;
    }

    public static List<String> getStationList() {
        return StationDao.getStationList();
    }

    public static List<String> getTrainList() {
        return TrainDao.getTrainList();
    }

    /**
     * Method returns the list of passengers, that bought tickets for given train
     *
     * @param tNumber Train number
     * @return List<Passenger> list of Passengers
     */
    public static List<PassengerList> getPassengerList(int tNumber){
        List<PassengerList> passengerList;

        List<Ticket> tickets = TicketDao.getTicketsForTrainNumber(tNumber); // getting the list

        // checking the list
        if (tickets == null || tickets.isEmpty()){ // if there is no results we returning the list which is empty
            return null;
        }
        else { // if the list is not empty
            passengerList = new ArrayList<PassengerList>();
            for (Ticket t : tickets) { // we filling the list of passengers
                PassengerList p = new PassengerList();
                UserDetails passenger = UserDetailsDao.getUser(t.getPassengerId()); // using passengerId, taken from list of tickets

                p.setName(passenger.getName());
                p.setSurname(passenger.getSurname());
                p.setDob(passenger.getDob());
                p.setSeat(t.getSeat());

                passengerList.add(p);
            }

            return passengerList;
        }
    }

    /**
     * Method to get Train`s row mapping according to Train`s template (his real-world cabin type)
     *
     * @return List<String> list of Trains` template name
     */
    public static List<String> getTemplateNames() {
        return TemplateTrainDao.getTemplateNames();
    }
}
