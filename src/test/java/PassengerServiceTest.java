import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import railroad.dto.MessageBean;
import railroad.dto.Offer;
import railroad.persistence.dao.*;
import railroad.service.PassengerService;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author vbuevich
 */

public class PassengerServiceTest extends SpringTestSupport {

    @Mock
    HttpSession session;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private SeatmapDao seatmapDao;

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private TemplateTrainDao templateTrainDao;

    @Autowired
    private TemplateRowsDao templateRowsDao;

    @Autowired
    private DaoFactory sessionFactory;


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void checkGetOffersNegativeDS() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should fail, wrong departure station"); // that Passenger never exists

        String depStation = "WRONG";
        String depTime = "10:00:00";
        String arrStation = "Moscow";
        String arrTime = "23:55:55";
        List<Offer> offerList = passengerService.getOffers(depStation, depTime, arrStation, arrTime);

        assertTrue(offerList.isEmpty());
    }

    @Test
    public void checkGetOffersNegativeDT() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should fail, wrong departure time"); // that Passenger never exists

        String depStation = "Saint Petersburg";
        String depTime = "WRONG";
        String arrStation = "Moscow";
        String arrTime = "23:55:55";
        List<Offer> offerList = passengerService.getOffers(depStation, depTime, arrStation, arrTime);

        assertTrue(offerList.isEmpty());
    }

    @Test
    public void checkGetOffersNegativeAS() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should fail, wrong arrival station"); // that Passenger never exists

        String depStation = "Saint Petersburg";
        String depTime = "10:00:00";
        String arrStation = "WRONG";
        String arrTime = "23:55:55";
        List<Offer> offerList = passengerService.getOffers(depStation, depTime, arrStation, arrTime);

        assertTrue(offerList.isEmpty());
    }

    @Test
    public void checkGetOffersNegativeAT() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should fail, wrong arrival time"); // that Passenger never exists

        String depStation = "Saint Petersburg";
        String depTime = "10:00:00";
        String arrStation = "Moscow";
        String arrTime = "WRONG";
        List<Offer> offerList = passengerService.getOffers(depStation, depTime, arrStation, arrTime);

        assertTrue(offerList.isEmpty());
    }

    @Test
    public void checkGetOffersPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should not be Ok, all data is fine"); // that Passenger never exists

        String depStation = "Saint Petersburg";
        String depTime = "10:00:00";
        String arrStation = "Moscow";
        String arrTime = "23:55:55";

        List<Offer> offerList = passengerService.getOffers(depStation, depTime, arrStation, arrTime);

        assertFalse(offerList.isEmpty());
    }

    @Test
    public void changePassTestNegativeUser() {

        System.out.println("-------------------");
        System.out.println("Testing ChangePass : negative : wrong email"); // that Employee never exists

        Boolean bool = passengerService.changePass("WRONG100500EMAIL", "qwerty");
        assertFalse(bool);
    }

    @Test
    public void changePassTestNegativePass() {

        System.out.println("-------------------");
        System.out.println("Testing ChangePass : negative : wrong reminder phrase"); // that Employee exists, but secret phrase is wrong

        Boolean bool = passengerService.changePass("javaschool.railroad@mail.ru", "qwerty");
        assertFalse(bool);
    }

    @Test
    public void testBuyTicketNegativeWrongUser() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong user");

        MessageBean message = new MessageBean();
        passengerService.buyTicket(0, "Saint Petersburg", "Moscow", 1001, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongDepartureStation() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong departure station");

        MessageBean message = new MessageBean();
        passengerService.buyTicket(1, "WRONG", "Moscow", 1001, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongArrivalStation() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong arrival station");

        MessageBean message = new MessageBean();
        passengerService.buyTicket(0, "Saint Petersburg", "WRONG", 1001, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongTrain() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong train");

        MessageBean message = new MessageBean();
        passengerService.buyTicket(0, "Saint Petersburg", "Moscow", 0, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongSeat() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong seat");

        MessageBean message = new MessageBean();
        passengerService.buyTicket(0, "Saint Petersburg", "Moscow", 1001, "WRONG", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testGetOccupiedSeatsPositive() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : positive");

        StringBuilder sb = passengerService.getOccupiedSeats("1001"); // this train number exists for sure
        assertTrue(sb.length() > 0);
    }

    @Test
    public void testGetOccupiedSeatsNegativeNull() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : negative : argument is null");

        StringBuilder sb = passengerService.getOccupiedSeats(null); // argument is null
        assertTrue(sb.length() == 0);
    }

    @Test
    public void testGetOccupiedSeatsNegativeIncorrect() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : negative : train number is incorrect");

        StringBuilder sb = passengerService.getOccupiedSeats("WRONG"); // argument is null
        assertTrue(sb.length() == 0);
    }

    @Test
    public void testGetOccupiedSeatsNegativeWrong() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : negative : train number is wrong");

        StringBuilder sb = passengerService.getOccupiedSeats("0"); // argument is wrong
        assertTrue(sb.length() == 0);
    }

    @Test
    public void testAddUserNegativeName() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : name is empty");

        String name = "";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass1 = "pass";
        String pass2 = "pass";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativeSurname() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : surname is empty");

        String name = "Ivan";
        String surname = "";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass1 = "pass";
        String pass2 = "pass";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativeDate() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : date is incorrect");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "Двадцатое мая";
        String email = "java@t-systems.ru";
        String pass1 = "pass";
        String pass2 = "pass";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativeEmail() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : email is incorrect");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "WRONG";
        String pass1 = "pass";
        String pass2 = "pass";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativeEmailExists() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : email already registered");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "JavaSchool777@mail.ru";
        String pass1 = "pass";
        String pass2 = "pass";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativePass() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : password is empty");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass1 = "";
        String pass2 = "pass";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativePhrase() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : secret phrase is empty");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass1 = "pass";
        String pass2 = "pass";
        String secret = "";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertFalse(isUserAdded);
    }

    @Ignore // due to the fact that we have to delete use each time
    @Test
    public void testAddUserPositive() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : positive");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass1 = "qwertyuiop";
        String pass2 = "qwertyuiop";
        String secret = "secret";
        MessageBean message = MessageBean.get(session);

        Boolean isUserAdded = passengerService.addUser(name, surname, dob, email, pass1, pass2, secret, false, message);

        assertTrue(isUserAdded);
    }

    @Ignore // Internet access required due to mail sending
    @Test
    public void changePassTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing ChangePass : positive");

        Boolean bool = passengerService.changePass("Neena.Kochhar@oracle.com", "secret");
        assertTrue(bool);
    }
}
