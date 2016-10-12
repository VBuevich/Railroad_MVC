package railroad.persistence.dao;

import railroad.persistence.entity.TemplateRows;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * @author vbuevich
 */
public class TemplateRowsDao {

    private static final Logger LOGGER = Logger.getLogger(StationDao.class);

    /**
     * Method to get Train`s row mapping according to Train`s template (his real-world cabin type)
     *
     * @param templateId type of real-world Train` cabin type
     * @return List<TemplateRows> seat map
     */
    public static List<TemplateRows> getRows(String templateId) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<TemplateRows> rows = null;

        try {
            Query q = session.createQuery("FROM TemplateRows t WHERE t.templateId = :templateId ORDER BY t.rowNumber");
            q.setParameter("templateId", templateId);

            rows = q.list();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return rows;
    }
}
