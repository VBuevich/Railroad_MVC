package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Station entity
 */
@Repository
public class StationDao {

    private final Logger LOGGER = Logger.getLogger(StationDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method that adds new station
     *
     * @param stationName Station Name
     * @return true is Station is successfully added
     */
    public Boolean addStation(String stationName) {

        Session session = sessionFactory.getSessionFactory().openSession();
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
    public List<String> getStationList() {
        Session session = sessionFactory.getSessionFactory().openSession();
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
