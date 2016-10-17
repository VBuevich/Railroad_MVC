import org.hibernate.query.Query;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import railroad.persistence.dao.TrainDao;
import railroad.persistence.entity.UserDetails;
import railroad.service.EmployeeService;
import railroad.dto.PassengerList;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static railroad.service.PassengerService.changePass;

/**
 * @author vbuevich
 */
public class EmployeeServiceTest {

    public EmployeeServiceTest() {
    }
    @Mock
    Query q;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException{
        MockitoAnnotations.initMocks(this);

        when((UserDetails)q.uniqueResult()).thenReturn(new UserDetails());
    }

    @After
    public void tearDown() {
    }

    @Ignore
    @Test
    public void changePassTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing ChangePass : positive"); // ignored in order to save time due to the fact that password is changed in random way
                                                             // and we need to retrieve new pass from email to run test again

        Boolean bool = changePass("JavaSchool7772@mail.ru", "secret");
        assertTrue(bool);
    }

    @Test
    public void changePassTestNegativeUser() {

        System.out.println("-------------------");
        System.out.println("Testing ChangePass : negative : wrong email"); // that Employee never exists

        Boolean bool = changePass("WRONG100500EMAIL", "qwerty");
        assertFalse(bool);
    }

    @Test
    public void changePassTestNegativePass() {

        System.out.println("-------------------");
        System.out.println("Testing ChangePass : negative : wrong reminder phrase"); // that Employee exists, but secret phrase is wrong

        Boolean bool = changePass("javaschool.railroad@mail.ru", "qwerty");
        assertFalse(bool);
    }

    @Test
    public void addTrainTestNegative() {

        System.out.println("-------------------");
        System.out.println("Testing if we can add duplicate train from EmployeeService");

        List<String> trainList = TrainDao.getTrainList();
        int trainNumber = Integer.parseInt(trainList.get(0)); // just first train , just for test

        Boolean bool = EmployeeService.addTrain(trainNumber, "TST"); // adding duplicate

        assertFalse(bool);
    }

    @Test
    public void getStationListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of stations");

        List<String> stationList = EmployeeService.getStationList();
        assertFalse(stationList.isEmpty()); // list is not empty
    }


    @Test
    public void getTrainListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of trains");

        List<String> trainList = EmployeeService.getTrainList();
        assertFalse(trainList.isEmpty()); // list is not empty
    }

    @Test
    public void getPassengerListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of passengers: positive test");

        List<PassengerList> passengerList = EmployeeService.getPassengerList(1001); // this train number exists for sure and there are passengers so it should be Ok
        assertFalse(passengerList.isEmpty()); // list is not empty
    }

    @Test
    public void getPassengerListTestNegative() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of passengers: negative test");

        List<PassengerList> passengerList = EmployeeService.getPassengerList(1); // this train number does not exist so it fails
        assertTrue(passengerList == null); // method returns null in case if receives wrong parameter train
    }

    @Test
    public void getTemplatesListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of trains");

        List<String> templateList = EmployeeService.getTemplateNames();
        assertFalse(templateList.isEmpty()); // list is not empty
    }
}
