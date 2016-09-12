package Railroad;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.*;

/**
 * Created by VBuevich on 30.08.2016.
 */
public class RailroadDao {

    public static Passenger checkPass(String email, String pass)
    {
        Session session = DaoFactory.getSessionFactory().openSession();
        Passenger passenger = null;
        String sha1password = DigestUtils.sha1Hex(pass);

        try {
            Query q = session.createQuery("FROM Railroad.Passenger WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            passenger = (Passenger)q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            System.err.println("Login Successful, passengerId: " + passenger.getPassengerId());
        }
        catch (Exception e) {
            System.err.println("Login usuccessful, passenger is unregistered");
            // method will return null is Exception is cought
        }
        finally {
            session.close();
            return passenger;
        }
    }

    public static Employee checkEmpl(String email, String pass)
    {
        Session session = DaoFactory.getSessionFactory().openSession();
        Employee employee = null;
        String sha1password = DigestUtils.sha1Hex(pass);

        try {
            Query q = session.createQuery("FROM Railroad.Employee WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            employee = (Employee)q.uniqueResult(); // uniqueResult could be received just in case if passenger found
            System.err.println("Login Successful, employeeId: " + employee.getEmployeeId());
        }
        catch (Exception e) {
            System.err.println("Login usuccessful, employee is unregistered");
            // method will return null is Exception is cought
        }
        finally {
            session.close();
            return employee;
        }
    }

    public static List<String> getStationList() {
        Session session = DaoFactory.getSessionFactory().openSession();
        ArrayList<String> stationList = new ArrayList<String>();
        try {
            Query q = session.createQuery("FROM Railroad.Station");
            List<Station> sList = q.list();

            for (Station s : sList) {
                stationList.add(s.getStationName());
            }

        }
        catch (Exception e) {
            stationList.add(e.getMessage());
            return stationList;
        }
        finally {
            session.close();
        }
        return stationList;
    }

    public static List<String> getTrainList() {
        Session session = DaoFactory.getSessionFactory().openSession();
        ArrayList<String> trainList = new ArrayList<String>();
        try {
            Query q = session.createQuery("FROM Railroad.Train");
            List<Train> tList = q.list();

            for (Train t : tList) {
                trainList.add(t.getTrainNumber().toString());
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            session.close();
            return trainList;
        }
    }

    public static String addStation(String stationName) {
        return "";
    }

    public static String addTrain(int trainNumber, int seats) {
        Session session = DaoFactory.getSessionFactory().openSession();

        try {
            Train t = new Train();
            t.setSeats(seats);
            t.setTrainNumber(trainNumber);
            session.save(t);
        }
        catch (Exception e) {
            return e.getMessage();
        }
        finally {
            session.close();
        }
        return "";
    }

    public static String buyTicket(int passengerId, String departureS, String arrivalS, int trainNumber) {
        Session session = DaoFactory.getSessionFactory().openSession();
        String result = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query q1 = session.createQuery("SELECT COUNT(*) FROM Railroad.Schedule WHERE trainNumber = :tn");
            q1.setParameter("tn", trainNumber);
            Integer count = ((Number)q1.uniqueResult()).intValue();
            System.err.println("Number of tickets sold: " + count);

            Query q2 = session.createQuery("SELECT seats FROM Railroad.Train WHERE trainNumber = :tn");
            q2.setParameter("tn", trainNumber);
            Object uniqueResult = q2.uniqueResult();
            if (uniqueResult == null) {
                result = "Ticket is not sold: Incorrect train number";
                return result;
            }
            Integer seats = ((Number)uniqueResult).intValue();
            System.err.println("Seats: " + seats);

            if (seats == count) {
                result = "Ticket is not sold: All seats are already sold for train number " + trainNumber;
                return result;
            }

            Query q3 = session.createQuery("SELECT COUNT(*) FROM Railroad.Ticket WHERE trainNumber = :tn AND passengerId = :pid");
            q3.setParameter("tn", trainNumber);
            q3.setParameter("pid", passengerId);
            Integer ticketsSoldForpassenger = ((Number)q3.uniqueResult()).intValue();
            if (ticketsSoldForpassenger == 1) {
                result = "Ticket is not sold: You have already bought ticket for this train";
                return result;
            }
            System.err.println("Passenger has bought ticket: " + ticketsSoldForpassenger);

            Query q4 = session.createQuery("SELECT time FROM Railroad.Schedule WHERE trainNumber = :tn AND stationName = :sn");
            q4.setParameter("tn", trainNumber);
            q4.setParameter("sn", departureS);
            Time timeOfDeparture = ((Time)q4.uniqueResult());

            Calendar calendarNow = Calendar.getInstance();
            Calendar calendarDep = Calendar.getInstance();
            calendarDep.setTime(timeOfDeparture);
            calendarDep.set(1970,1,1);
            calendarNow.set(1970,1,1);
            long difference = calendarDep.getTimeInMillis() - calendarNow.getTimeInMillis();

            System.err.println("Difference: " + difference + " millisecondes between Departure and Now");
            if (difference < 600000) {
                result = "Ticket is not sold: There is less than 10 minutes before departure";
                return result;
            }

            result = "Trying to buy a ticket. Unsuccessful due to internal reasons if you see this message";
            Ticket t = new Ticket();
            t.setArrivalStation(arrivalS);
            t.setDepartureStation(departureS);
            t.setOneWay(true);
            t.setPassengerId(passengerId);
            t.setTrainNumber(trainNumber);
            t.setPassengerByPassengerId((Passenger)session.get(Passenger.class, passengerId));
            t.setStationByArrivalStation((Station)session.get(Station.class, arrivalS));
            t.setStationByDepartureStation((Station)session.get(Station.class, departureS));
            t.setTrainByTrainNumber((Train)session.get(Train.class, trainNumber));

            session.save(t);
            tx.commit();
            result = "You have successfully bought ticket from " + departureS + " to " + arrivalS + " , train #" + trainNumber;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();

        }
        finally {
            session.close();
            return result;
        }
    }

    public static List<Schedule> trainList(String stationName) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Schedule> trainList = null;

        try {
            Query q = session.createQuery("FROM Railroad.Schedule WHERE stationName = :st ORDER BY time");
            q.setParameter("st", stationName);

            trainList = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return trainList;
    }

    public static List<Schedule> scheduleForDeparture(String stationName, String departureTime) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Schedule> trainList = null;

        try {
            Query q = session.createQuery("FROM Railroad.Schedule s WHERE s.stationName = :st AND TIME(s.time) > TIME(:departureTime)");
            q.setParameter("st", stationName);
            q.setParameter("departureTime", departureTime);

            trainList = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return trainList;
    }

    public static List<Schedule> scheduleForArrival(String stationName, String arrivalTime) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Schedule> trainList = null;

        try {
            Query q = session.createQuery("FROM Railroad.Schedule s WHERE s.stationName = :st AND TIME(s.time) < TIME(:arrivalTime)");

            q.setParameter("st", stationName);
            q.setParameter("arrivalTime", arrivalTime);
            trainList = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return trainList;
    }
}
