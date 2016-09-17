/*
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;
*/
/**
 * Created by VBuevich on 14.09.2016.
 */
/*
    public class LoginCheckTest extends Mockito {

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

            // verify(request, atLeast(1)).getParameter("email"); // only if you want to verify username was called...
            verify(request).getParameter("email");
            verify(request).getParameter("pass");
            verify(request).getParameter("Employee");
            writer.flush(); // it may not have been flushed yet...
            //assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8")
            //        .contains("My Expected String"));
        }
    }

*/