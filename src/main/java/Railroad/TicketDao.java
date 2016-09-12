package Railroad;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by VBuevich on 11.09.2016.
 */
public class TicketDao {
    public static List<Ticket> getTickets(int userId) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Ticket> tickets = null;

        try {
            Query q = session.createQuery("FROM Railroad.Ticket s WHERE s.passengerId = :userId");

            q.setParameter("userId", userId);
            tickets = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return tickets;
    }

    public static List<Ticket> getTicketsForTrainNumber(int tNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Ticket> tickets = null;

        try {
            Query q = session.createQuery("FROM Railroad.Ticket s WHERE s.trainNumber = :tNumber");

            q.setParameter("tNumber", tNumber);
            tickets = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return tickets;
    }

    public static String addStation(int trainNumber) {

        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;
        String result = null;

        try {
            tx = session.beginTransaction();

            Train t = new Train();
            t.setTrainNumber(trainNumber);
            t.setSchedulesByTrainNumber(null);
            t.setTicketsByTrainNumber(null);
            t.setSeats(80);

            session.save(t);
            tx.commit();
        }
        catch (Exception e) {
            result = "Unsuccessful due to internal reasons if you see this message";
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();
        }
        finally {
            session.close();
            return result;
        }
    }
}
