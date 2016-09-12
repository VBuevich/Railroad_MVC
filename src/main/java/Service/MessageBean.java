package Service;

import Railroad.Employee;

import javax.servlet.http.HttpSession;

/**
 * Created by vbuevich on 12.09.2016.
 */
public class MessageBean {

    private String errorMessage = null;
    private String successMessage = null;

    public static MessageBean get(HttpSession session) {
        MessageBean message = (MessageBean) session.getAttribute("message");
        if (message == null) {
            message = new MessageBean();
            session.setAttribute("message", message);
        }
        return message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
