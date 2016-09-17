package Persistence.Dao;

import Persistence.Entity.Passenger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

/**
 * @author vbuevich
 *
 * DAO class for Passenger entity
 */
public class PassengerDao {

    private static final Logger LOGGER = Logger.getLogger(PassengerDao.class);

    /**
     *
     * @param userId primary key of Passenger entity
     * @return Passenger entity for given key
     */
    public static Passenger getPassenger(int userId) {
        Session session = DaoFactory.getSessionFactory().openSession(); // Hibernate session
        Passenger p = null;

        try {
            Query q = session.createQuery("FROM Passenger p WHERE p.passengerId = :userId");

            q.setParameter("userId", userId);
            p = (Passenger)q.uniqueResult(); // due to the fact that passengerId is primary key we can get just unique result
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return p;
    }
}
