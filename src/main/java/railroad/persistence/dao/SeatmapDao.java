package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import railroad.persistence.entity.Seatmap;
import railroad.persistence.entity.TemplateSeats;
import railroad.persistence.entity.Train;

import java.util.List;

/**
 * Created by VBuevich on 19.09.2016.
 */
public class SeatmapDao {

    private static final Logger LOGGER = Logger.getLogger(ScheduleDao.class);

    /**
     * Method creates new set of seatmap records, used when adding new train
     *
     * @param trainNumber Train number
     * @param session Hibernate session - opened in EmployeeService due to the fat that the method is transactional
     * @return true is success, otherwise false
     */
    public static Boolean createSeatmap(int trainNumber, String templateId, Session session) {

        Boolean isSuccess = false;

        try {
             List<TemplateSeats> seats = TemplateSeatsDao.getSeats(templateId);


            for (int i = 0; i < seats.size(); i++) {

                Seatmap s = new Seatmap(); // creating new instance of Seatmap (1 seat in newly created train)
                s.setTrainNumber(trainNumber);
                s.setTrainByTrainNumber((Train) session.get(Train.class, trainNumber));
                s.setSeat(seats.get(i).getSeat());
                s.setUserDetailsByPassengerOwner(null);
                session.save(s); // persisting an instance of ticket. Now it will be saved in the database.
            }
            isSuccess = true;
            LOGGER.info("Seatmap added");

        } catch (Exception e) {
            LOGGER.error("Seatmap NOT added, " + e.getMessage());

        }
        return isSuccess;
    }

    /**
     * Method to get occupied seats in given train
     *
     * @param trainNumber Train number
     * @return List<Seatmap> of occupied seats either null if no reults found
     */
    public static List<Seatmap> getOccupiedSeats(int trainNumber) {

        List<Seatmap> sm = null;
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
            Query q = session.createQuery("FROM Seatmap s WHERE s.trainNumber = :tn AND s.passengerOwner != null");
            q.setParameter("tn", trainNumber);

            sm = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return sm;
    }

    /**
     * Method checks if selected seat is available in selected train
     *
     * @param trainNumber Train
     * @param selectedSeat Selected seat
     * @return true if seat is still vacant either false if occupied
     */
    public static Boolean isSeatAvailable(int trainNumber, String selectedSeat) {
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
            Query q = session.createQuery("FROM Seatmap s WHERE s.trainNumber = :tn AND s.seat = :seat");
            q.setParameter("tn", trainNumber);
            q.setParameter("seat", selectedSeat);

            Seatmap s = (Seatmap)q.uniqueResult(); // uniqueResult returns an object if query is successfull either null if not
            if (s.getPassengerOwner() == null) {
                return true; // seat is vacant
            }
            else {
                return false; // seat is occupied
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false; // something goes wrong, so we won`t sell ticket
        }
        finally {
            session.close();
        }
    }
}


