package railroad.service;

import railroad.persistence.dao.*;
import railroad.persistence.entity.Employee;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import railroad.persistence.entity.Passenger;
import railroad.persistence.entity.Ticket;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.ArrayList;
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
     * Method, used during Log-in of employee
     *
     * @param email Log-in field of Employee
     * @param pass Password
     * @return Employee object if Ok either null is log-in unsuccessfull
     */
    public static Employee checkEmpl(String email, String pass)
    {
        Session session = DaoFactory.getSessionFactory().openSession();
        Employee employee = null;
        String sha1password = DigestUtils.sha1Hex(pass); // Due to the fact that all passwords are encrypted in database,
                                                         // firstly we getting an encrypted value of string field of password

        try {
            Query q = session.createQuery("FROM Employee WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            employee = (Employee)q.uniqueResult(); // uniqueResult could be received just in case if passenger found
        }
        catch (Exception e) {
            // method will return null if Exception is caught
            // LOGGER of invoker method will log this case when receives null
        }
        finally {
            session.close(); // we always closing Hibernate Session
            return employee;
        }
    }

    /**
     * Method checks if user entered valid "secret phrase", generates new password and sends an email confirmation
     *
     * @param email User`s email
     * @param secret User`s secret phrase
     * @return true if password successfully changed, false in case of any error
     */
    public static Boolean changePass(String email, String secret)
    {
        Boolean isSuccess = false; // flag for success return
        Boolean isCorrect = EmployeeDao.checkSecret(email, secret); // checking secret phrase
        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;

        if (isCorrect) {

            SecureRandom random = new SecureRandom(); // secure randomizer
            String newPassword = new BigInteger(130, random).toString(32); // generating new secure password
            String sha1password = DigestUtils.sha1Hex(newPassword); // encrypting new password
            try {
                tx = session.beginTransaction(); // we are doing transaction due to the fact that we need to be sure that: 1) password changed AND 2) email sent
                Boolean newPassSuccess = EmployeeDao.setPassword(sha1password, email, session); // set new password
                Boolean isMailSuccess = false;
                if (newPassSuccess) { // if successfully changed - sending a confirmation email
                    isMailSuccess = mailer.send("Password change for railroad site", "You have successfully changed your password: " + newPassword, "javaschool.railroad@gmail.com", email);
                }
                if (newPassSuccess && isMailSuccess) { // is both success
                    isSuccess = true; // for success return
                }
                else {
                    if (tx != null) tx.rollback(); // rollback if email has not been sent
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                if (tx != null) tx.rollback(); // rollback if any exception is caught
            } finally {
                session.close(); // we always closing Hibernate session
            }
        }
        return isSuccess;
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
                Passenger passenger = PassengerDao.getPassenger(t.getPassengerId()); // using passengerId, taken from list of tickets

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
