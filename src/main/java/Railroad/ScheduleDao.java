package Railroad;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Time;

/**
 * Created by vbuevich on 13.09.2016.
 */
public class ScheduleDao {

    public static String addSchedule(int tNumber, String station, Time departureTime) {

        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;
        String result = null;

        try {
            tx = session.beginTransaction();

            Schedule s = new Schedule();
            s.setStationName(station);
            s.setStationByStationName((Station)session.get(Station.class, station));
            s.setTime(departureTime);
            s.setTrainNumber(tNumber);
            s.setTrainByTrainNumber((Train)session.get(Train.class, tNumber));

            session.save(s);
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
