package Persistence.Service;

import Persistence.Dao.DaoFactory;
import Persistence.Dao.EmployeeDao;
import Persistence.Dao.SeatmapDao;
import Persistence.Dao.TrainDao;
import Persistence.Entity.Employee;
import Service.Mailer;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * @author vbuevich
 *
 * Service class for DAO requests of Employee activity
 */
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);
    private static Mailer mailer = new Mailer();

    /**
     * Method, used during Log-in of employee
     *
     * @param email Log-in field of Employee
     * @param pass Password
     * @return Employee object if Ok either null is log-in unsuccessfull
     */
    public static Employee checkEmpl(String email, String pass)
    {
        Session session = DaoFactory.getSessionFactory().openSession();
        Employee employee = null;
        String sha1password = DigestUtils.sha1Hex(pass); // Due to the fact that all passwords are encrypted in database,
                                                         // firstly we getting an encrypted value of string field of password

        try {
            Query q = session.createQuery("FROM Employee WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            employee = (Employee)q.uniqueResult(); // uniqueResult could be received just in case if passenger found
        }
        catch (Exception e) {
            // method will return null if Exception is caught
            // LOGGER of invoker method will log this case when receives null
        }
        finally {
            session.close();
            return employee; // we always closing Hibernate Session
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
        Boolean isCorrect = EmployeeDao.checkSecret(email, secret); // checking secret phrase
        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;

        if (isCorrect) {

            SecureRandom random = new SecureRandom(); // secure randomizer
            String newPassword = new BigInteger(130, random).toString(32); // generating new secure password
            String sha1password = DigestUtils.sha1Hex(newPassword); // encrypting new password
            try {
                tx = session.beginTransaction(); // we are doing transaction due to the fact that we need to be sure that: 1) password changed AND email sent
                Boolean newPassSuccess = EmployeeDao.setPassword(sha1password, email, session); // set new password
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
     * Method adds new Train and new Seatmap for it
     *
     * @param trainNumber Train number
     * @return true if Train and Seatmap were successfully added, false in case of error
     */
    public static Boolean addTrain(int trainNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;
        Boolean isSuccess = false;

        try {
            tx = session.beginTransaction(); // we are doing transaction due to the fact that we are wrighting to different tables

            Boolean addTrain =  TrainDao.addTrain(trainNumber, session); // adding Traing
            Boolean addSeatmap = SeatmapDao.createSeatmap(trainNumber, session); // adding Seatmap

            if (addTrain && addSeatmap) { // if both success
                tx.commit(); // successfull commit
                isSuccess = true; // so we return true - success
            }
            else { // if we have just partial success - we doing rollback
                if (tx != null) tx.rollback();
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (tx != null) tx.rollback();
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return isSuccess;
    }

}
