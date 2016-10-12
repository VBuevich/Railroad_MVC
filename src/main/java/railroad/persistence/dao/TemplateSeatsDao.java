package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import railroad.persistence.entity.TemplateSeats;

import java.util.List;

/**
 * @author vbuevich
 */
public class TemplateSeatsDao {

    private static final Logger LOGGER = Logger.getLogger(StationDao.class);

    public static List<TemplateSeats> getSeats(String templateId) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<TemplateSeats> seats = null;

        try {
            Query q = session.createQuery("FROM TemplateSeats t WHERE t.templateId = :templateId");
            q.setParameter("templateId", templateId);

            seats = q.list();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return seats;
    }
}
