import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by VBuevich on 14.09.2016.
 */
public class TestLoginCheck {

    public class TestMyServlet extends Mockito {

        @Test
        public void testServlet() throws Exception {
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);

            when(request.getParameter("email")).thenReturn("VBuevich777@yahoo.com");
            when(request.getParameter("pass")).thenReturn("JS777JS");
            when(request.getParameter("status")).thenReturn("Employee");
            PrintWriter writer = new PrintWriter("somefile.txt");
            when(response.getWriter()).thenReturn(writer);

            new RailServlet.LoginCheck().doPost(request, response);

            verify(request, atLeast(1)).getParameter("email"); // only if you want to verify username was called...
            writer.flush(); // it may not have been flushed yet...
            assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8")
                    .contains("My Expected String"));
        }
    }
}
