package Railroad;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by vbuevich on 12.09.2016.
 */
public class PassengerDao {
    public static Passenger getPassenger(int userId) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Passenger p = null;

        try {
            Query q = session.createQuery("FROM Railroad.Passenger p WHERE p.passengerId = :userId");

            q.setParameter("userId", userId);
            p = (Passenger)q.uniqueResult();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return p;
    }
}
