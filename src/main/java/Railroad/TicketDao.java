package Railroad;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Ticket entity
 */
public class TicketDao {

    private static final Logger LOGGER = Logger.getLogger(TicketDao.class);

    /**
     * Method that returns the list of tickets for given passenger
     *
     * @param userId ID of passenger, primary key
     * @return List<Ticket> list of tickets for given passenger
     */
    public static List<Ticket> getTickets(int userId) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Ticket> tickets = null;

        try {
            Query q = session.createQuery("FROM Railroad.Ticket s WHERE s.passengerId = :userId");

            q.setParameter("userId", userId);
            tickets = q.list();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate Session
        }
        return tickets;
    }

    /**
     * Method that returns the list of tickets for given train
     *
     * @param tNumber ID of train , primary key
     * @return List<Ticket> list of tickets for given train
     */
    public static List<Ticket> getTicketsForTrainNumber(int tNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Ticket> tickets = null;

        try {
            Query q = session.createQuery("FROM Railroad.Ticket s WHERE s.trainNumber = :tNumber");

            q.setParameter("tNumber", tNumber);
            tickets = q.list();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate Session
        }
        return tickets;
    }

    /**
     * Method return the number of sold tickets for given train
     *
     * @param trainNumber Train number
     * @return number of tickets sold
     */
    public static int getNumberOfSoldTickets (int trainNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();

        Integer count = 1;

        try {
            Query q1 = session.createQuery("SELECT COUNT(*) FROM Railroad.Ticket WHERE trainNumber = :tn");
            q1.setParameter("tn", trainNumber);
            count = ((Number) q1.uniqueResult()).intValue();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate Session
        }

        return count;
    }

    /**
     * Method checks if passenger has already bought a ticket for given train
     *
     * @param trainNumber Train number
     * @param passengerId passenger ID, primary key
     * @return true if passenger already have bought ticket, false if not.
     */
    public static Boolean ifPassengerAlreadyBoughtTicket(int trainNumber, int passengerId) {
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
            Query q3 = session.createQuery("SELECT COUNT(*) FROM Railroad.Ticket WHERE trainNumber = :tn AND passengerId = :pid");
            q3.setParameter("tn", trainNumber);
            q3.setParameter("pid", passengerId);
            Integer ticketsSoldForPassenger = ((Number) q3.uniqueResult()).intValue(); // unique result due to the fact that primary key
            if (ticketsSoldForPassenger == 1) { // it could be just 0 or 1 (passenger is unable to buy multiple tickets on same train)
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return true;
        }
        finally {
                session.close(); // we always closing Hibernate Session
        }
    }

}
