package Persistence.Service;

import Persistence.Dao.*;
import Persistence.Entity.*;
import Service.Mailer;
import Service.MessageBean;
import Service.Offer;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * @author vbuevich
 *
 * Service class for DAO requests of Passenger activity
 */
public class PassengerService {

    private static final Logger LOGGER = Logger.getLogger(PassengerService.class);
    private static Mailer mailer = new Mailer();

    /**
     * Method returns the list of "Offers" that meets passenger`s demands. Each offer has details of ticket that passenger can buy
     *
     * @param depStation Departure Station
     * @param depTime Departure Time
     * @param arrStation Arrival Station
     * @param arrTime Arrival Time
     * @return List<Offer> List of 'Offers' that Passenger could buy
     */
    public static List<Offer> getOffers(String depStation, String depTime, String arrStation, String arrTime) {

        List<Offer> offers = new ArrayList<Offer>();

        // First of all, we getting the list of trains that meets the requirements for departure:
        // this is a list of trains that departs from chosen station after chosen time
        List<Schedule> listForDeparture = ScheduleDao.scheduleForDeparture(depStation, depTime);

        // Then, we getting the list of trains that meets the requirements for arrival:
        // this is a list of trains that arrives to chosen station before chosen time
        List<Schedule> listForArrival = ScheduleDao.scheduleForArrival(arrStation, arrTime);

        // Then we process these two lists to get the offer for passenger:
        // - it should be the same train in both lists
        // - time of departure should be _before_ the time of arrival, what tells us that this is a train in right direction
        for (Schedule sD : listForDeparture) { // double loop
            for (Schedule sA : listForArrival) {
                if (sD.getTrainNumber().equals(sA.getTrainNumber())) { // if this is a same train
                    long difference = sD.getTime().getTime() - sA.getTime().getTime(); // we getting time in milliseconds
                    if (difference < 0) { // if time of arrival is bigger (in fact - later) than arrival time
                        Offer offer = new Offer(); // we have found new offer for passenger and creating a new instance of Offer
                        offer.setArrivalStation(sA.getStationName()); // Arrival Station
                        offer.setArrivalTime(sA.getTime()); // Arrival Time
                        offer.setDepartureStation(sD.getStationName()); // Departure Station
                        offer.setDepartureTime(sD.getTime()); // Departure Time
                        offer.setTrainNumber(sD.getTrainNumber()); // Train number
                        offers.add(offer); // adding it to list of offers
                    }
                }
            }
        }

        return offers;
    }

    /**
     * Method returns the list of passengers, that bought tickets for given train
     *
     * @param tNumber Train number
     * @return List<Passenger> list of Passengers
     */
    public static List<Passenger> getPassengerList(int tNumber){
        List<Passenger> passengerList = null;

        List<Ticket> tickets = TicketDao.getTicketsForTrainNumber(tNumber); // getting the list

        // checking the list
        if (tickets == null || tickets.isEmpty()){ // if there is no results we returning the list which is empty
            return passengerList;
        }
        else { // if the list is not empty
            passengerList = new ArrayList<Passenger>();
            for (Ticket t : tickets) { // we filling the list of passengers
                passengerList.add(PassengerDao.getPassenger(t.getPassengerId())); // using passengerId, taken from list of tickets
             }

        return passengerList;
        }
    }

    /**
     * Method, used during Log-in of passenger
     *
     * @param email Log-in field of Passenger
     * @param pass Password
     * @return Passenger object if Ok either null is log-in unsuccessfull
     */
    public static Passenger checkPass(String email, String pass)
    {
        Session session = DaoFactory.getSessionFactory().openSession();
        Passenger passenger = null;
        String sha1password = DigestUtils.sha1Hex(pass); // Due to the fact that all passwords are encrypted in database,
                                                         // firstly we getting an encrypted value of string field of password

        try {
            Query q = session.createQuery("FROM Passenger WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            passenger = (Passenger)q.uniqueResult(); // uniqueResult could be received just in case if passenger found
        }
        catch (Exception e) {
            // method will return null is Exception is cought
            // LOGGER of invoker method will log this case when receives null
        }
        finally {
            session.close(); // we always closing Hibernate Session
            return passenger;
        }
    }

    /**
     * Method, used for use-case "Passenger is buying ticket"
     *
     * @param passengerId Passenger ID
     * @param departureS Departure station
     * @param arrivalS Arrival station
     * @param trainNumber Train number
     * @param message MessageBean to get access for information messages
     */
    public static void buyTicket(int passengerId, String departureS, String arrivalS, int trainNumber, String selectedSeat, MessageBean message) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction(); // we are doing transaction due to the fact that we are wrighting to different tables

            // checking if passenger has already bought ticket for chosen train
            Boolean check = TicketDao.ifPassengerAlreadyBoughtTicket(trainNumber, passengerId);
            if (check) { // invocked method returns true if passenger already bought a ticket
                message.setErrorMessage("Ticket is not sold: You have already bought ticket for this train");
                LOGGER.info("Ticket is not sold: Passenger have already bought ticket for this train");
                return;
            }

            // then we need to check the availability of chosen seat - if it is still vacant:
            Boolean seatStillAvailable = SeatmapDao.isSeatAvailable(trainNumber, selectedSeat);

            if (!seatStillAvailable) { // false means that the seat is occupied already
                message.setErrorMessage("Ticket is not sold: Selected seat is already occupied. Train number " + trainNumber + " , seat number " + selectedSeat);
                LOGGER.info("Selected seat has just been sold. Train number " + trainNumber + " , seat number " + selectedSeat);
                return;
            }

            // Then, we need to check if passenger still have time to get into the train.
            // getting the scheduled time of departure
            Time timeOfDeparture = ScheduleDao.getTimeOfDeparture(trainNumber, departureS);

            // checking if passenger have at least 10 minutes
            Calendar calendarNow = Calendar.getInstance(); // an instance of Calendar - we need to know "time now"
            Calendar calendarDep = Calendar.getInstance(); // an instance of Calendar - to use with departure
            calendarDep.setTime(timeOfDeparture); // setting the time of scheduled departure
            calendarDep.set(1970,1,1); // here we setting the same Date for both instances so we can compare times
            calendarNow.set(1970,1,1); // +
            long difference = calendarDep.getTimeInMillis() - calendarNow.getTimeInMillis(); // and we getting the difference

            // System.err.println("Difference: " + difference + " millisecondes between Departure and Now");
            if (difference < 600000) { // 600000 milliseconds means 10 minutes
                message.setErrorMessage("Ticket is not sold: There is less than 10 minutes before departure");
                LOGGER.info("Ticket is not sold: There is less than 10 minutes before departure");
                return;
            }

            // if everything is fine - we can sell a ticket
            Ticket t = new Ticket(); // creating new instance of ticket
            t.setArrivalStation(arrivalS);
            t.setDepartureStation(departureS);
            t.setOneWay(true);
            t.setPassengerId(passengerId);
            t.setTrainNumber(trainNumber);
            t.setSeat(selectedSeat);
            t.setPassengerByPassengerId((Passenger)session.get(Passenger.class, passengerId));
            t.setStationByArrivalStation((Station)session.get(Station.class, arrivalS));
            t.setStationByDepartureStation((Station)session.get(Station.class, departureS));
            t.setTrainByTrainNumber((Train)session.get(Train.class, trainNumber));

            session.save(t); // persisting an instance of ticket. Now it will be saved in the database.

            Query query = session.createQuery("UPDATE Seatmap s SET s.passengerOwner = :pass where s.trainNumber = :trainNumber AND s.seat = :seat");
            query.setParameter("pass", passengerId);
            query.setParameter("trainNumber", trainNumber);
            query.setParameter("seat", selectedSeat);
            query.executeUpdate();

            tx.commit(); // successfull commit

            String success = "You have successfully bought ticket from " + departureS + " to " + arrivalS + " , train #" + trainNumber + " , seat #" + selectedSeat;
            message.setSuccessMessage(success);
            LOGGER.info(success);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (tx != null) tx.rollback();
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
    }

    /**
     * Method returns the list of occupied seats for given train
     *
     * @param trainNumber Train number
     * @return StringBuilder containing XML tree of occupied seats
     */
    public static StringBuilder getOccupiedSeats(String trainNumber) {

        StringBuilder sb = new StringBuilder();
        int tNumber = 0;
        try {
            tNumber = Integer.parseInt(trainNumber);
        }
        catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            return sb;
        }

        List<Seatmap> sm = SeatmapDao.getOccupiedSeats(tNumber); // retreiving list of objects - collection of Seatmap objects with occupied seats

        // Creating XML
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<seatmap>");

        for (Seatmap s : sm) {
            sb.append("<seat_number>").append(s.getSeat()).append("</seat_number>");
        }
        sb.append("</seatmap>");
        return sb;
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
        Boolean isCorrect = PassengerDao.checkSecret(email, secret); // checking secret phrase
        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;

        if (isCorrect) {

            SecureRandom random = new SecureRandom(); // secure randomizer
            String newPassword = new BigInteger(130, random).toString(32); // generating new secure password
            String sha1password = DigestUtils.sha1Hex(newPassword); // encrypting new password
            try {
                tx = session.beginTransaction(); // we are doing transaction due to the fact that we need to be sure that: 1) password changed AND email sent
                Boolean newPassSuccess = PassengerDao.setPassword(sha1password, email, session); // set new password
                Boolean isMailSuccess = false;
                if (newPassSuccess) { // if successfully changed - sending a confirmation email
                    isMailSuccess = mailer.send("Password change for Railroad site", "You have successfully changed your password: " + newPassword, "javaschool.railroad@gmail.com", email);
                }
                if (newPassSuccess && isMailSuccess) { // is both success
                    isSuccess = true; // for success return
                }
                else {
                    if (tx != null) tx.rollback(); // rollback if email has not been sent
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                if (tx != null) tx.rollback(); // rollback if email has not been sent
            } finally {
                session.close(); // we always closing Hibernate session
            }
        }
        return isSuccess;
    }

    /**
     * Method for register new user
     *
     * @param name User`s name
     * @param surname User`s surname
     * @param dob User`s date of birth
     * @param email User`s e-mail
     * @param pass User`s password
     * @param secret User`s secret phrase
     * @return true is success, false if error
     */
    public static boolean addUser(String name, String surname, String dob, String email, String pass, String secret) {

        String sha1password = DigestUtils.sha1Hex(pass);

        int year, month, day = 0;

        try {
            String yearS = dob.substring(0, 4);
            String monthS = dob.substring(5, 7);
            String dayS = dob.substring(8, 10);

            year = Integer.parseInt(yearS);
            month = Integer.parseInt(monthS);
            day = Integer.parseInt(dayS);
        }
        catch (Exception e) {
            // message.setErrorMessage("Invalid time");
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.set(year,month,day,0,0,0);
        Date date = new java.sql.Date(c.getTimeInMillis());

        Boolean isSuccess = PassengerDao.addUser(name, surname, date, email, sha1password, secret);

        return isSuccess;
    }
}
