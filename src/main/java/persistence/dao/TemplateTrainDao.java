package persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import persistence.entity.TemplateTrain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vbuevich
 */
public class TemplateTrainDao {

    private static final Logger LOGGER = Logger.getLogger(StationDao.class);

    /**
     * Method to get Train`s row mapping according to Train`s template (his real-world cabin type)
     *
     * @return List<String> list of Trains` template name
     */
    public static List<String> getTemplateNames() {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<TemplateTrain> templates;
        List<String> templateNames = null;

        try {
            Query q = session.createQuery("FROM TemplateTrain");
            templates = q.list();
            if (templates != null) {
                templateNames = new ArrayList<String>();
                for (TemplateTrain t : templates) {
                    templateNames.add(t.getTemplateId());
                }
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close(); // we always closing Hibernate session
        }
        return templateNames;
    }
}
