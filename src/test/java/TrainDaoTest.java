import org.hibernate.Session;
import org.junit.*;
import persistence.dao.DaoFactory;
import persistence.dao.TrainDao;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by vbuevich on 11.10.2016.
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

        TrainDao.addTrain(trainNumber, "TST", session);

        session.getTransaction().commit();

    }


}
