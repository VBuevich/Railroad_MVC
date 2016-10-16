package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import railroad.persistence.entity.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Station entity
 */
public class StationDao {

    private static final Logger LOGGER = Logger.getLogger(StationDao.class);

    /**
     * Method that adds new station
     *
     * @param stationName Station Name
     * @return true is Station is successfully added
     */
    public static Boolean addStation(String stationName) {

        Session session = DaoFactory.getSessionFactory().openSession();
        Transaction tx = null;
        Boolean isSuccess = true; // true if success

        try {
            tx = session.beginTransaction();

            // new entity of Station
            Station s = new Station();
            s.setStationName(stationName);
            s.setSchedulesByStationName(null);
            s.setTicketsByStationName(null);
            s.setTicketsByStationName_0(null);

            session.save(s);
            tx.commit();
        }
        catch (Exception e) {
            isSuccess = false; // setting to false if not success
            LOGGER.error(e.getMessage());
            if (tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
        return isSuccess;
    }

    /**
     * Method that returns the list of Stations
     *
     * @return List<String> station names either empty list if encounter error
     */
    public static List<String> getStationList() {
        Session session = DaoFactory.getSessionFactory().openSession();
        ArrayList<String> stationList = new ArrayList<String>();
        try {
            Query q = session.createQuery("FROM Station");
            List<Station> sList = q.list(); // getting list of Station objects

            for (Station s : sList) { // getting the name of station from each Station object
                stationList.add(s.getStationName()); // adding name of station to list
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return stationList;
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return stationList;
    }
}
