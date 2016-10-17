package railroad.persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.persistence.entity.TemplateRows;

import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for TemplateRows entity
 */
@Repository
public class TemplateRowsDao {

    private final Logger LOGGER = Logger.getLogger(StationDao.class);

    @Autowired
    private DaoFactory sessionFactory;

    /**
     * Method to get Train`s row mapping according to Train`s template (his real-world cabin type)
     *
     * @param templateId type of real-world Train` cabin type
     * @return List<TemplateRows> seat map
     */
    public List<TemplateRows> getRows(String templateId) {
        Session session = sessionFactory.getSessionFactory().openSession();
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
