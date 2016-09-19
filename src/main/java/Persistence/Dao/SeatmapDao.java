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

    public static void createSeatmap(int trainNumber) {

        Session session = DaoFactory.getSessionFactory().openSession();

        try {

            for (int i = 0; i < SeatmapDao.seats.length; i++) {
                Seatmap s = new Seatmap(); // creating new instance of ticket
                s.setTrainByTrainNumber((Train) session.get(Train.class, trainNumber));
                s.setSeat(seats[i]);
                s.setPassengerByPassengerOwner(null);

                session.save(s); // persisting an instance of ticket. Now it will be saved in the database.
            }

            LOGGER.info("Schedule added");
        } catch (Exception e) {
            LOGGER.error("Schedule NOT added, " + e.getMessage());

        } finally {
            session.close(); // we always closing Hibernate session
        }
    }

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
}


