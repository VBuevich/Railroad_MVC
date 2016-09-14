package Railroad;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

/**
 * @author vbuevich
 *
 * Service class for DAO requests of Employee activity
 */
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);

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
            Query q = session.createQuery("FROM Railroad.Employee WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            employee = (Employee)q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            System.err.println("Login Successful, employeeId: " + employee.getEmployeeId());
        }
        catch (Exception e) {
            // method will return null is Exception is cought
            // LOGGER of invoker method will log this case when receives null
        }
        finally {
            session.close();
            return employee; // we always closing Hibernate Session
        }
    }

}
