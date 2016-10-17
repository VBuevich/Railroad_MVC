package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.Statistics;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Statistics entity
 */
@Repository
public class StatisticsDao {

    private final Logger LOGGER = Logger.getLogger(StationDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method that returns the list of Statistics records
     *
     * @return List<Statistics> list of statistics records either empty list if encounter error
     */
    public List<Statistics> getStatistics() {
        Session session = sessionFactory.getSessionFactory().openSession();
        ArrayList<Statistics> statistics = new ArrayList<Statistics>();
        try {
            Query q = session.createQuery("FROM Statistics");
            statistics = (ArrayList<Statistics>)q.list(); // getting list of Statistics objects

        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return statistics;
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return statistics;
    }

    /**
     * Method that returns the list of Statistics records for requested period of time
     *
     * @return List<Statistics> list of statistics records either empty list if encounter error
     */
    public List<Statistics> getStatistics(String startTime, String endTime) {
        if (startTime == null || endTime == null) return null;

        Session session = sessionFactory.getSessionFactory().openSession();
        ArrayList<Statistics> statistics = new ArrayList<Statistics>();
        try {
            Query q = session.createQuery("FROM Statistics WHERE datetime > :startTime AND datetime < :endTime");
            Date sT = new Date(new Long(startTime));
            Date eT = new Date(new Long(endTime));
            q.setParameter("startTime", sT);
            q.setParameter("endTime", eT);
            statistics = (ArrayList<Statistics>)q.list(); // getting list of Statistics objects

        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return statistics;
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return statistics;
    }

}
