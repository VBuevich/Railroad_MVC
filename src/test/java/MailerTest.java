import railroad.service.Mailer;
import org.junit.*;

import javax.mail.internet.AddressException;

import static org.junit.Assert.assertTrue;

/**
 * @author vbuevich
 */
public class MailerTest {

    public MailerTest() {
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

    @Ignore
    @Test
    public void testSendEmail() throws Exception {

        System.out.println("-------------------");
        System.out.println("Testing if Email is sending: should be Ok");
        Mailer mailer = new Mailer();
        Boolean shouldBeOk = mailer.send("This is Subject", "SSL: This is text!", "javaschool.railroad@gmail.com", "javaschool.railroad@gmail.com");
        assertTrue(shouldBeOk);
    }

    @Test(expected = AddressException.class)
    public void testWrongOutEmail() throws Exception {

        System.out.println("-------------------");
        System.out.println("Testing what if sender Email is wrong");
        Mailer mailer = new Mailer();
        mailer.send("This is Subject", "SSL: This is text!", "WRONG100500EMAIL", "javaschool.railroad@gmail.com");
    }

    @Test(expected = AddressException.class)
    public void testWrongInEmail() throws Exception {

        System.out.println("-------------------");
        System.out.println("Testing what if receiver Email is wrong");
        Mailer mailer = new Mailer();
        mailer.send("This is Subject", "SSL: This is text!", "javaschool.railroad@gmail.com", "WRONG100500EMAIL");
    }

}
