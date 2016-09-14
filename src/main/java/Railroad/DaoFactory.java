package Railroad;

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
        configuration.addAnnotatedClass(Railroad.Employee.class);
        configuration.addAnnotatedClass(Railroad.Passenger.class);
        configuration.addAnnotatedClass(Railroad.Schedule.class);
        configuration.addAnnotatedClass(Railroad.Station.class);
        configuration.addAnnotatedClass(Railroad.Ticket.class);
        configuration.addAnnotatedClass(Railroad.Train.class);

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
