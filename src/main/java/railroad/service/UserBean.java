package railroad.service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Bean for user, containing useful fields for his activity
 *
 * @author VBuevich
 */
public class UserBean {

    private String name;
    private String surname;
    private int userId;
    private List<Offer> offerList = null;

    /**
     *
     * @param session HTTPSession
     * @return Bean for user
     */
    public static UserBean get(HttpSession session) {
        UserBean bean = (UserBean) session.getAttribute("bean");
        if (bean == null) {
            bean = new UserBean();
            session.setAttribute("bean", bean);
        }
        return bean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }
}
