package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import railroad.persistence.entity.User;

import java.sql.Date;

/**
 * @author vbuevich
 *
 * DAO class for User entity
 */
public class UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class);

    /**
     * Method to change User`s secret phrase
     *
     * @param email User`s email
     * @param secret User`s secret phrase
     * @return true if secret phrase is correct
     */
    public static Boolean checkSecret(String email, String secret) {
        Session session = DaoFactory.getSessionFactory().openSession();
        User user;
        Boolean isSuccess = false;

        try {
            Query q = session.createQuery("FROM User u WHERE u.email = :em AND u.passRecovery = :secret");
            q.setParameter("em", email);
            q.setParameter("secret", secret);

            user = (User) q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            if (user != null) { // if we have Employee object - then the combination of email and secret was correct
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
     * @param session Hibernate session, opened in EmployeeService as we doing transaction
     * @return
     */
    public static Boolean setPassword(String pass, String email, Session session) {
        Boolean isSuccess = false;
        try {
            Query query = session.createQuery("UPDATE User u SET u.password = :pass WHERE u.email = :email");
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
     * Method to get user using his Id
     *
     * @param userId primary key of User entity
     * @return User entity for given key
     */
    public static User getUser(int userId) {
        Session session = DaoFactory.getSessionFactory().openSession(); // Hibernate session
        User p = null;

        try {
            Query q = session.createQuery("FROM User u WHERE u.userId = :userId");

            q.setParameter("userId", userId);
            p = (User)q.uniqueResult(); // due to the fact that userId is primary key we can get just unique result
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
     * Method to add new user
     *
     * @param name Users` name
     * @param surname Users` surname
     * @param dob Users` Date of birth
     * @param email Users` Email
     * @param pass Users` password
     * @param secret Users` secret phrase user for password retrieval
     * @return true if success, otherwise false
     */
    public static Boolean addUser(String name, String surname, Date dob, String email, String pass, String secret, String userRole) {
        Boolean isSuccess = false; // flag for success return
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
            User p = new User();
            p.setName(name);
            p.setSurname(surname);
            p.setDob(dob);
            p.setEmail(email);
            p.setPassword(pass);
            p.setPassRecovery(secret);
            p.setSeatmapsByUserId(null);
            p.setTicketsByUserId(null);
            p.setUserRole(userRole);
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
