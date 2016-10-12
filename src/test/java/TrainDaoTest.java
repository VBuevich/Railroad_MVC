import org.hibernate.Session;
import org.junit.*;
import railroad.persistence.dao.DaoFactory;
import railroad.persistence.dao.TrainDao;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * @author vbuevich
 */
public class TrainDaoTest {

    public TrainDaoTest() {
    }


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(expected = PersistenceException.class)
    public void tryAddDuplicateTrain() throws PersistenceException {

        Session session = DaoFactory.getSessionFactory().openSession();
        session.beginTransaction();

        System.out.println("-------------------");
        System.out.println("Testing if we can add duplicate train");

        List<String> trainList = TrainDao.getTrainList();
        int trainNumber = Integer.parseInt(trainList.get(0)); // just first train , just for test

        TrainDao.addTrain(trainNumber, "TST", session); // adding duplicate

        session.getTransaction().commit(); // throws PersistenceException

    }


}
