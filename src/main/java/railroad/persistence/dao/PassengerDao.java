package railroad.persistence.dao;

import railroad.persistence.entity.Passenger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import java.sql.Date;

/**
 * @author vbuevich
 *
 * DAO class for Passenger entity
 */
public class PassengerDao {

    private static final Logger LOGGER = Logger.getLogger(PassengerDao.class);

    /**
     * Method to get passenger using his Id
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

    /**
     * Method checks user`s secret phrase
     *
     * @param email Passenger`s email
     * @param Secret Passenger`s secret phrase
     * @return true if prase is correct, othervise false
     */
    public static Boolean checkSecret(String email, String Secret) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Passenger passenger;
        Boolean isSuccess = false;

        try {
            Query q = session.createQuery("FROM Passenger p WHERE p.email = :em AND p.passRecovery = :secret");
            q.setParameter("em", email);
            q.setParameter("secret", Secret);

            passenger = (Passenger) q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            if (passenger != null) { // null in case if there is no combination email-secret
                isSuccess = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            session.close();
        }
        return isSuccess;
    }

    /**
     * Method to change User`s password
     *
     * @param pass User`s NEW password
     * @param email User`s email
     * @param session Hibernate session, opened in PassengerService as we doing transaction
     * @return true is success, otherwise false
     */
    public static Boolean setPassword(String pass, String email, Session session) {
        Boolean isSuccess = false;
        try {
            Query query = session.createQuery("UPDATE Passenger p SET p.password = :pass WHERE p.email = :email");
            query.setParameter("pass", pass);
            query.setParameter("email", email);
            query.executeUpdate();

            isSuccess = true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return isSuccess;
    }

    /**
     * Method to add new passenger
     *
     * @param name Passenger`s name
     * @param surname Passenger`s surname
     * @param dob Passenger`s Date of birth
     * @param email Passenger`s Email
     * @param pass Passenger`s password
     * @param secret Passenger`s secret phrase user for password retrieval
     * @return true if success, otherwise false
     */
    public static Boolean addUser(String name, String surname, Date dob, String email, String pass, String secret) {
        Boolean isSuccess = false; // flag for success return
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
             Passenger p = new Passenger();
             p.setName(name);
             p.setSurname(surname);
             p.setDob(dob);
             p.setEmail(email);
             p.setPassword(pass);
             p.setPassRecovery(secret);
             p.setSeatmapsByPassengerId(null);
             p.setTicketsByPassengerId(null);
             session.save(p);
             isSuccess = true;
            }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }

        return isSuccess;
    }
}

