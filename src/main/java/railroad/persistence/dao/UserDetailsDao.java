package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.UserDetails;

import java.sql.Date;

/**
 * @author vbuevich
 *
 * DAO class for UserDetails entity
 */
@Repository
public class UserDetailsDao {

    private final Logger LOGGER = Logger.getLogger(UserDetailsDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method to change UserDetails`s secret phrase
     *
     * @param email UserDetails`s email
     * @param secret UserDetails`s secret phrase
     * @return true if secret phrase is correct
     */
    public Boolean checkSecret(String email, String secret) {
        Session session = sessionFactory.getSessionFactory().openSession();
        UserDetails userDetails;
        Boolean isSuccess = false;

        try {
            Query q = session.createQuery("FROM UserDetails u WHERE u.email = :em AND u.passRecovery = :secret");
            q.setParameter("em", email);
            q.setParameter("secret", secret);

            userDetails = (UserDetails) q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            if (userDetails != null) { // if we have Employee object - then the combination of email and secret was correct
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
     * Method to change UserDetails`s password
     *
     * @param pass UserDetails`s NEW password
     * @param email UserDetails`s email
     * @param session Hibernate session, opened in Service as we doing transaction
     * @return
     */
    public Boolean setPassword(String pass, String email, Session session) {
        Boolean isSuccess = false;
        try {
            Query query = session.createQuery("UPDATE UserDetails u SET u.password = :pass WHERE u.email = :email");
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
     * @param userId primary key of UserDetails entity
     * @return UserDetails entity for given key
     */
    public UserDetails getUser(int userId) {
        Session session = sessionFactory.getSessionFactory().openSession(); // Hibernate session
        UserDetails p = null;

        try {
            Query q = session.createQuery("FROM UserDetails u WHERE u.userId = :userId");

            q.setParameter("userId", userId);
            p = (UserDetails)q.uniqueResult(); // due to the fact that userId is primary key we can get just unique result
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
    public Boolean addUser(String name, String surname, Date dob, String email, String pass, String secret, String userRole, Boolean enabled) {
        Boolean isSuccess = false; // flag for success return
        Session session = sessionFactory.getSessionFactory().openSession();

        try {
            UserDetails p = new UserDetails();
            p.setName(name);
            p.setSurname(surname);
            p.setDob(dob);
            p.setEmail(email);
            p.setPassword(pass);
            p.setPassRecovery(secret);
            p.setSeatmapsByUserId(null);
            p.setTicketsByUserId(null);
            p.setUserRole(userRole);
            p.setEnabled(enabled);
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
