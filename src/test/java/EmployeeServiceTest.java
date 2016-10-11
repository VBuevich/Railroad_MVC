import org.hibernate.query.Query;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistence.entity.Employee;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static persistence.service.EmployeeService.checkEmpl;

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

        when((Employee)q.uniqueResult()).thenReturn(new Employee());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void checkEmplTest() {

        System.out.println("-------------------");
        System.out.println("Testing if Employee is checking correctly: should be Ok"); // that Employee exists in DB for sure

        Employee e = checkEmpl("javaschool.railroad@gmail.com", "JS777JS");
        assertTrue(e != null);
    }

}
