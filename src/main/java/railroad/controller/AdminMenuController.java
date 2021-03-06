package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import railroad.persistence.dao.StatisticsDao;
import railroad.persistence.entity.Statistics;
import railroad.dto.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author vbuevich
 *
 */
@Controller
public class AdminMenuController {

    @Autowired
    private StatisticsDao statisticsDao;

    /**
     * Forwards to adminMenu.jsp after successful login
     *
     * @param request
     * @param model
     * @return forward to adminMenu.jsp
     */
    @RequestMapping("/admin/adminMenu")
    public String adminMenu(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        UserBean bean = UserBean.get(session); // session-scoped DTO

        model.addAttribute("name", bean.getName());
        model.addAttribute("surname", bean.getSurname());

        return "adminMenu";
    }

    @RequestMapping("/admin/getStatistics")
    public ModelAndView downloadExcel(HttpServletRequest request, Model model) {

        // create some sample data
        List<Statistics> statistics = statisticsDao.getStatistics();

        // return a view which will be resolved by a view resolver
        return new ModelAndView("pdfView", "statistics", statistics);
    }

}
