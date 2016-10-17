package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.Schedule;
import railroad.persistence.entity.Train;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Train entity
 */
@Repository
public class TrainDao {

    private final Logger LOGGER = Logger.getLogger(TrainDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method that returns the list of train names
     *
     * @return List<String> train numbers
     */
    public List<String> getTrainList() {
        Session session = sessionFactory.getSessionFactory().openSession();

        ArrayList<String> trainList = new ArrayList<String>();
        try {
            Query q = session.createQuery("FROM Train ORDER BY 1");
            List<Train> tList = q.list(); // getting the list of Train entities

            for (Train t : tList) {
                trainList.add(Integer.toString(t.getTrainNumber())); // getting the list of Train names
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
    public List<Schedule> trainList(String stationName) {
        Session session = sessionFactory.getSessionFactory().openSession();
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
     * Method to add new train
     *
     * @param trainNumber Train numbers
     * @param session Hibernate session, opened in EmployeeService due to the fact that method is transactional
     * @return true if success, otherwise false
     */
    public Boolean addTrain(int trainNumber, String templateId, Session session) throws ConstraintViolationException {

        Boolean isSuccess = true;

            // crating new instance of Train
            Train t = new Train();
            t.setTrainNumber(trainNumber);
            t.setTemplateId(templateId);
            session.save(t); // persisting

        return isSuccess;
    }

    public String getTemplateId(int trainNumber) {
        Session session = sessionFactory.getSessionFactory().openSession();
        String templateId = null;

        try {
            Query q = session.createQuery("FROM Train t WHERE t.trainNumber = :trainNumber");
            q.setParameter("trainNumber", trainNumber);

            Train train = ((Train)q.uniqueResult()); // uniqueness is granted by database table Unique restriction
            templateId = train.getTemplateId();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return templateId;
    }
}
