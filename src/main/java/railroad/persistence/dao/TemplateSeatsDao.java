package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.TemplateSeats;

import java.util.List;

/**
 * @author vbuevich
 */
@Repository
public class TemplateSeatsDao {

    private final Logger LOGGER = Logger.getLogger(StationDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method to get Train`s seats according to Train`s template (his real-world cabin type)
     *
     * @param templateId type of real-world Train` cabin type
     * @return List<TemplateSeats> seatmap
     */
    public List<TemplateSeats> getSeats(String templateId) {
        Session session = sessionFactory.getSessionFactory().openSession();
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
