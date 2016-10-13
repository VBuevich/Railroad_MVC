package railroad.service;

import railroad.persistence.dao.*;
import railroad.persistence.entity.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * @author vbuevich
 *
 * railroad.service class for DAO requests of Passenger activity
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
        if (listForDeparture != null && listForArrival != null) { // perhaps we received null from one of there methods due to error
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
        }

        return offers;
    }

    /**
     * Method, used during Log-in of passenger
     *
     * @param email Log-in field of Passenger
     * @param pass Password
     * @return Passenger object if Ok either null is log-in unsuccessful
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
            LOGGER.error(e.getMessage());
            // method will return null is Exception is cought
            // LOGGER of invoker method will log this case when receives null
        }
        finally {
            session.close(); // we always closing Hibernate Session
            return passenger;
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
                if (tx != null) tx.rollback(); // rollback if email has not been sent
            } finally {
                session.close(); // we always closing Hibernate session
            }
        }
        return isSuccess;
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
            Time timeOfDeparture = ScheduleDao.getTime(trainNumber, departureS);

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

            Time timeOfArrival = ScheduleDao.getTime(trainNumber, arrivalS);
            Passenger p = PassengerDao.getPassenger(passengerId);
            StringBuilder eTicket = new StringBuilder();
            eTicket.append("Dear " + p.getSurname() + " " + p.getName() + System.lineSeparator());
            eTicket.append("Please kindly find your e-Ticket details: " + System.lineSeparator());
            eTicket.append(System.lineSeparator());
            eTicket.append("Train #" + trainNumber + " , seat #" + selectedSeat + " , from " + departureS + " to " + arrivalS + "." + System.lineSeparator());
            eTicket.append("Departure time : " + timeOfDeparture + ", arrival time : " + timeOfArrival + System.lineSeparator());
            eTicket.append(System.lineSeparator());
            eTicket.append("Thank you for choosing our webservice!" + System.lineSeparator());
            eTicket.append("With best regards," + System.lineSeparator());
            eTicket.append("RailRoad administration." + System.lineSeparator());
            Boolean isMailSuccess = mailer.send("RailRoad site : Your electronic ticket", eTicket.toString(), "javaschool.railroad@gmail.com", p.getEmail());
            if (isMailSuccess) {
                LOGGER.info("e-Ticket successfully sent.");
            }
            else {
                LOGGER.info("e-Ticket was not sent.");
            }

            java.util.Date date= new java.util.Date();
            Statistics s = new Statistics();

            s.setDatetime(new Timestamp(date.getTime()));
            s.setPassengerName(p.getName());
            s.setPassengerSurname(p.getSurname());
            s.setPassengerDob(p.getDob());
            s.setPassengerEmail(p.getEmail());
            s.setTrainNumber(trainNumber);
            s.setTrainType(TrainDao.getTemplateId(trainNumber));
            s.setDepartureStation(departureS);
            s.setArrivalStation(arrivalS);
            s.setSeat(selectedSeat);
            s.setIsOneWay(true);
            s.setDepartureDate(new Date(date.getTime()));
            s.setDepartureTime(timeOfDeparture);

            session.save(s); // persisting an instance of Statistics. Now it will be saved in the database.

            tx.commit(); // successfull commit

            String success = "You have successfully bought ticket from " + departureS + " to " + arrivalS + " , train #" + trainNumber + " , seat #" + selectedSeat + ". Please check your EMail for e-Ticket.";
            message.setSuccessMessage(success);
            LOGGER.info("Passenger has successfully bought ticket from " + departureS + " to " + arrivalS + " , train #" + trainNumber + " , seat #" + selectedSeat);

        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            message.setErrorMessage("Ticket is not sold due to internal reasons. Please try again either ask administrator.");
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

        String templateId = TrainDao.getTemplateId(tNumber); // we need to get the type of Train`s seatmap - his TemplateId
        List<TemplateRows> tr = TemplateRowsDao.getRows(templateId); // retrieving list of rows for selected templateId
        List<Seatmap> sm = SeatmapDao.getOccupiedSeats(tNumber); // retrieving list of objects - collection of Seatmap objects with occupied seats

        if (tr.size() == 0) { // zero size could be in case if train number is wrong
            return sb;
        }

        // Creating XML
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<seatmap>");

        for (Seatmap s : sm) {
            sb.append("<unavailable>").append(s.getSeat()).append("</unavailable>"); // collection of occupied seats
        }
        for (TemplateRows t : tr) {
            sb.append("<row>").append(t.getRowSeats()).append("</row>"); // collection of rows
        }
        sb.append("</seatmap>");

        return sb;
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
