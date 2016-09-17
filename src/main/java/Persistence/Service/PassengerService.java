package Persistence.Service;

import Persistence.Dao.*;
import Persistence.Entity.*;
import Service.MessageBean;
import Service.Offer;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

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

    /**
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
    public static void buyTicket(int passengerId, String departureS, String arrivalS, int trainNumber, MessageBean message) {
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
            // first of all, we need to check the awailability of seats for ticketing:
            // getting the number of sold tickets
            int numberOfSoldTickets = TicketDao.getNumberOfSoldTickets(trainNumber);
            // and the number of seats in train (maximum availability)
            int seats = TrainDao.getSeats(trainNumber);

            // comparing these numbers
            if (seats <= numberOfSoldTickets) { // true means we reached maximum availability, all tickets sold. Less in case of SQL error
                message.setErrorMessage("Ticket is not sold: All seats are already sold for train number " + trainNumber);
                LOGGER.info("Ticket is not sold: All seats are already sold for train number " + trainNumber);
                return;
            }

            // checking if passenger has already bought ticket for chosen train
            Boolean check = TicketDao.ifPassengerAlreadyBoughtTicket(trainNumber, passengerId);
            if (check) { // invocked method returns true if passenger already bought a ticket
                message.setErrorMessage("Ticket is not sold: You have already bought ticket for this train");
                LOGGER.info("Ticket is not sold: Passenger have already bought ticket for this train");
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
            t.setPassengerByPassengerId((Passenger)session.get(Passenger.class, passengerId));
            t.setStationByArrivalStation((Station)session.get(Station.class, arrivalS));
            t.setStationByDepartureStation((Station)session.get(Station.class, departureS));
            t.setTrainByTrainNumber((Train)session.get(Train.class, trainNumber));

            session.save(t); // persisting an instance of ticket. Now it will be saved in the database.

            String s = "You have successfully bought ticket from " + departureS + " to " + arrivalS + " , train #" + trainNumber;
            message.setSuccessMessage(s);
            LOGGER.info(s);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());

        }
        finally {
            session.close(); // we always closing Hibernate session
        }
    }
}
