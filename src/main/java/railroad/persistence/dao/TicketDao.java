package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.Ticket;

import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Ticket entity
 */
@Repository
public class TicketDao {

    private final Logger LOGGER = Logger.getLogger(TicketDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method that returns the list of tickets for given passenger
     *
     * @param userId ID of passenger, primary key
     * @return List<Ticket> list of tickets for given passenger
     */
    public List<Ticket> getTickets(int userId) {
        Session session = sessionFactory.getSessionFactory().openSession();
        List<Ticket> tickets = null;

        try {
            Query q = session.createQuery("FROM Ticket s WHERE s.passengerId = :userId");

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
    public List<Ticket> getTicketsForTrainNumber(int tNumber) {
        Session session = sessionFactory.getSessionFactory().openSession();
        List<Ticket> tickets = null;

        try {
            Query q = session.createQuery("FROM Ticket s WHERE s.trainNumber = :tNumber");

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
     * Method checks if passenger has already bought a ticket for given train
     *
     * @param trainNumber Train number
     * @param passengerId passenger ID, primary key
     * @return true if passenger already have bought ticket, false if not.
     */
    public Boolean ifPassengerAlreadyBoughtTicket(int trainNumber, int passengerId) {
        Session session = sessionFactory.getSessionFactory().openSession();

        try {
            Query q3 = session.createQuery("SELECT COUNT(*) FROM Ticket WHERE trainNumber = :tn AND passengerId = :pid");
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
