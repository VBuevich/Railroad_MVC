package Railroad;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by VBuevich on 12.09.2016.
 */
public class StationDao {

    public static String addStation(String stationName) {

        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;
        String result = null;

        try {
            tx = session.beginTransaction();

            Station s = new Station();
            s.setStationName(stationName);
            s.setSchedulesByStationName(null);
            s.setTicketsByStationName(null);
            s.setTicketsByStationName_0(null);

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
