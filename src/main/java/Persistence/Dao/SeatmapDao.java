package Persistence.Dao;

import Persistence.Entity.Seatmap;
import Persistence.Entity.Train;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VBuevich on 19.09.2016.
 */
public class SeatmapDao {

    private static final Logger LOGGER = Logger.getLogger(ScheduleDao.class);
    private static final String seats[] = {"1_1","1_3","1_4",
            "2_1","2_3","2_4",
            "3_1","3_3","3_4",
            "4_1","4_3","4_4",
            "5_1","5_3","5_4",
            "6_1","6_3","6_4",
            "7_1","7_3","7_4",
            "8_1","8_3","8_4",
            "9_1","9_3","9_4",
            "10_1","10_3","10_4",
            "11_1","11_3","11_4",
            "12_1","12_3","12_4",
            "13_1","13_3","13_4",
            "14_1","14_3","14_4",
            "15_1","15_3","15_4"};

    /**
     * Method creates new set of seatmap records, used when adding new train
     *
     * @param trainNumber Train number
     * @param session Hibernate session - opened in EmployeeService due to the fat that the method is transactional
     * @return true is success, otherwise false
     */
    public static Boolean createSeatmap(int trainNumber, Session session) {

        Boolean isSuccess = false;

        try {

            for (int i = 0; i < SeatmapDao.seats.length; i++) {
                Seatmap s = new Seatmap(); // creating new instance of ticket
                s.setTrainByTrainNumber((Train) session.get(Train.class, trainNumber));
                s.setSeat(seats[i]);
                s.setPassengerByPassengerOwner(null);

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


