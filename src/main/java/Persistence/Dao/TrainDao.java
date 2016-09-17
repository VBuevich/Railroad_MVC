package Persistence.Dao;

import Persistence.Entity.Schedule;
import Persistence.Entity.Train;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Train entity
 */
public class TrainDao {

    private static final Logger LOGGER = Logger.getLogger(TrainDao.class);

    /**
     * Method that returns the list of train names
     *
     * @return List<String> train numbers
     */
    public static List<String> getTrainList() {
        Session session = DaoFactory.getSessionFactory().openSession();

        ArrayList<String> trainList = new ArrayList<String>();
        try {
            Query q = session.createQuery("FROM Train");
            List<Train> tList = q.list(); // getting the list of Train entities

            for (Train t : tList) {
                trainList.add(t.getTrainNumber().toString()); // getting the list of Train names
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session

            return trainList;
        }
    }

    /**
     * Method returns the list of stations for given Station
     *
     * @param stationName Station name
     * @return List<Schedule> list of schedules
     */
    public static List<Schedule> trainList(String stationName) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Schedule> trainList = null;

        try {
            Query q = session.createQuery("FROM Schedule WHERE stationName = :st ORDER BY time");
            q.setParameter("st", stationName);

            trainList = q.list();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return trainList;
    }

    /**
     * Method that adds new Train
     *
     * @param trainNumber Train number
     * @return true if success , false if fail
     */
    public static Boolean addTrain(int trainNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();

        Transaction tx = null;
        Boolean isSuccess = true;
        try {
            tx = session.beginTransaction();

            // crating new instance of Train
            Train t = new Train();
            t.setSeats(80);
            t.setTrainNumber(trainNumber);
            session.save(t); // persisting
            tx.commit();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            isSuccess = false;
            if (tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
        return isSuccess;
    }

    /**
     * Method that returns numbers of seats for given train
     *
     * @param trainNumber Train number
     * @return number of seats
     */
    public static int getSeats(int trainNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();
        Integer seats = 0;

        try {
            Query q2 = session.createQuery("SELECT seats FROM Train WHERE trainNumber = :tn");
            q2.setParameter("tn", trainNumber);
            Object uniqueResult = q2.uniqueResult();
            seats = ((Number) uniqueResult).intValue();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close();
        }
        return seats;
    }

}
