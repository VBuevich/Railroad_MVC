package Railroad;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by VBuevich on 30.08.2016.
 */
public class DaoFactory {

    static private SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration().configure();

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

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }



}
