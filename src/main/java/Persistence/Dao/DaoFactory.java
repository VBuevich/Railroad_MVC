package Persistence.Dao;

import Persistence.Entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author vbuevich
 *
 * Factory for SessionFactory, contains static field of SessionFactory and a getter
 * In order to initialise the factory the static block was used.
 */
public class DaoFactory {

    static private SessionFactory sessionFactory; // Static session factory, single-tone

    /**
     * Static block is executed once when the class is loaded (on application start)
     */
    static {
        Configuration configuration = new Configuration().configure();

        // adding Hibernate mapped classes
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Passenger.class);
        configuration.addAnnotatedClass(Schedule.class);
        configuration.addAnnotatedClass(Station.class);
        configuration.addAnnotatedClass(Ticket.class);
        configuration.addAnnotatedClass(Train.class);
        configuration.addAnnotatedClass(Seatmap.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        DaoFactory.sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    /**
     * Getter for SessionFactory object
     * @return Singletone SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
