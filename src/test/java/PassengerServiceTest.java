import org.hibernate.query.Query;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import railroad.persistence.entity.Passenger;
import railroad.service.MessageBean;
import railroad.service.Offer;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static railroad.service.PassengerService.*;

/**
 * @author vbuevich
 */
public class PassengerServiceTest {

    public PassengerServiceTest() {
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
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when((Passenger)q.uniqueResult()).thenReturn(new Passenger());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void checkPassTestPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if Passenger is checking correctly: should be Ok"); // that Employee exists in DB for sure

        Passenger p = checkPass("javaschool.railroad@gmail.com", "JS777JS");
        assertTrue(p != null);
    }

    @Test
    public void checkPassTestNeg() {

        System.out.println("-------------------");
        System.out.println("Testing if Passenger is checking correctly: should not be Ok"); // that Passenger never exists

        Passenger p = checkPass("Java@ee.com", "qwerty");
        assertTrue(p == null);
    }

    @Test
    public void checkGetOffersPositive() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should not be Ok, all data is fine"); // that Passenger never exists

        String depStation = "Saint Petersburg";
        String depTime = "10:00:00";
        String arrStation = "Moscow";
        String arrTime = "23:55:55";
        List<Offer> offerList = getOffers(depStation, depTime, arrStation, arrTime);

        assertFalse(offerList.isEmpty());
    }

    @Test
    public void checkGetOffersNegativeDS() {

        System.out.println("-------------------");
        System.out.println("Testing if we can get list of Offers : should fail, wrong departure station"); // that Passenger never exists

        String depStation = "WRONG";
        String depTime = "10:00:00";
        String arrStation = "Moscow";
        String arrTime = "23:55:55";
        List<Offer> offerList = getOffers(depStation, depTime, arrStation, arrTime);

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
        List<Offer> offerList = getOffers(depStation, depTime, arrStation, arrTime);

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
        List<Offer> offerList = getOffers(depStation, depTime, arrStation, arrTime);

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
        List<Offer> offerList = getOffers(depStation, depTime, arrStation, arrTime);

        assertTrue(offerList.isEmpty());
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
    public void testBuyTicketNegativeWrongUser() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong user");

        MessageBean message = new MessageBean();
        buyTicket(0, "Saint Petersburg", "Moscow", 1001, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongDepartureStation() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong departure station");

        MessageBean message = new MessageBean();
        buyTicket(1, "WRONG", "Moscow", 1001, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongArrivalStation() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong arrival station");

        MessageBean message = new MessageBean();
        buyTicket(0, "Saint Petersburg", "WRONG", 1001, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongTrain() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong train");

        MessageBean message = new MessageBean();
        buyTicket(0, "Saint Petersburg", "Moscow", 0, "1_1", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testBuyTicketNegativeWrongSeat() {

        System.out.println("-------------------");
        System.out.println("Testing Ticketing : negative : wrong seat");

        MessageBean message = new MessageBean();
        buyTicket(0, "Saint Petersburg", "Moscow", 1001, "WRONG", message);
        assertTrue(message.getErrorMessage() != null);
    }

    @Test
    public void testGetOccupiedSeatsPositive() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : positive");

        MessageBean message = new MessageBean();
        StringBuilder sb = getOccupiedSeats("1001"); // this train number exists for sure
        assertTrue(sb.length() > 0);
    }

    @Test
    public void testGetOccupiedSeatsNegativeNull() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : negative : argument is null");

        MessageBean message = new MessageBean();
        StringBuilder sb = getOccupiedSeats(null); // argument is null
        assertTrue(sb.length() == 0);
    }

    @Test
    public void testGetOccupiedSeatsNegativeIncorrect() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : negative : train number is incorrect");

        MessageBean message = new MessageBean();
        StringBuilder sb = getOccupiedSeats("WRONG"); // argument is null
        assertTrue(sb.length() == 0);
    }

    @Test
    public void testGetOccupiedSeatsNegativeWrong() {

        System.out.println("-------------------");
        System.out.println("Testing getOccupiedSeats : negative : train number is wrong");

        MessageBean message = new MessageBean();
        StringBuilder sb = getOccupiedSeats("0"); // argument is null
        assertTrue(sb.length() == 0);
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
        String pass = "pass";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativeName() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : name is empty");

        String name = "";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass = "pass";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

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
        String pass = "pass";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

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
        String pass = "pass";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

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
        String pass = "pass";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

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
        String pass = "pass";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

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
        String pass = "";
        String secret = "secret";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

        assertFalse(isUserAdded);
    }

    @Test
    public void testAddUserNegativePhrase() {

        System.out.println("-------------------");
        System.out.println("Testing addUser : negative : email is incorrect");

        String name = "Ivan";
        String surname = "Ivanov";
        String dob = "2000-05-24";
        String email = "java@t-systems.ru";
        String pass = "pass";
        String secret = "";

        Boolean isUserAdded = addUser(name, surname, dob, email, pass, secret);

        assertFalse(isUserAdded);
    }


}
