package persistence.dao;

import persistence.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

/**
 * @author vbuevich
 *
 * DAO class for Employee entity
 */
public class EmployeeDao {

    private static final Logger LOGGER = Logger.getLogger(EmployeeDao.class);

    /**
     * Method to change User`s secret phrase
     *
     * @param email User`s email
     * @param secret User`s secret phrase
     * @return true if secret phrase is correct
     */
    public static Boolean checkSecret(String email, String secret) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Employee employee;
        Boolean isSuccess = false;

        try {
            Query q = session.createQuery("FROM Employee e WHERE e.email = :em AND e.passRecovery = :secret");
            q.setParameter("em", email);
            q.setParameter("secret", secret);

            employee = (Employee) q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            if (employee != null) { // if we have Employee object - then the combination of email and secret was correct
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
            Query query = session.createQuery("UPDATE Employee e SET e.password = :pass WHERE e.email = :email");
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

}
