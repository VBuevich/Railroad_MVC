import com.sun.jersey.spi.inject.Inject;
import org.hibernate.query.Query;
import org.hibernate.service.spi.InjectService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import railroad.persistence.dao.*;
import railroad.persistence.entity.UserDetails;
import railroad.service.EmployeeService;
import railroad.dto.PassengerList;
import railroad.service.PassengerService;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author vbuevich
 */
public class EmployeeServiceTest {

    @InjectMocks
    private PassengerService passengerService;
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private ScheduleDao scheduleDao;
    @Mock
    private UserDetailsDao userDetailsDao;
    @Mock
    private StationDao stationDao;
    @Mock
    private TicketDao ticketDao;
    @Mock
    private SeatmapDao seatmapDao;
    @Mock
    private TrainDao trainDao;
    @Mock
    private TemplateTrainDao templateTrainDao;
    @Mock
    private TemplateRowsDao templateRowsDao;
    @Mock
    private DaoFactory sessionFactory;

    public EmployeeServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException{

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addTrainTestNegative() {

        System.out.println("-------------------");
        System.out.println("Testing if we can add duplicate train from EmployeeService");

        List<String> trainList = trainDao.getTrainList();
        int trainNumber = Integer.parseInt(trainList.get(0)); // just first train , just for test

        Boolean bool = employeeService.addTrain(trainNumber, "TST"); // adding duplicate

        assertFalse(bool);
    }

    @Test
    public void getStationListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of stations");

        List<String> stationList = employeeService.getStationList();
        assertFalse(stationList.isEmpty()); // list is not empty
    }


    @Test
    public void getTrainListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of trains");

        List<String> trainList = employeeService.getTrainList();
        assertFalse(trainList.isEmpty()); // list is not empty
    }

    @Test
    public void getPassengerListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of passengers: positive test");

        List<PassengerList> passengerList = employeeService.getPassengerList(1001); // this train number exists for sure and there are passengers so it should be Ok
        assertFalse(passengerList.isEmpty()); // list is not empty
    }

    @Test
    public void getPassengerListTestNegative() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of passengers: negative test");

        List<PassengerList> passengerList = employeeService.getPassengerList(1); // this train number does not exist so it fails
        assertTrue(passengerList == null); // method returns null in case if receives wrong parameter train
    }

    @Test
    public void getTemplatesListTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of trains");

        List<String> templateList = employeeService.getTemplateNames();
        assertFalse(templateList.isEmpty()); // list is not empty
    }
}
