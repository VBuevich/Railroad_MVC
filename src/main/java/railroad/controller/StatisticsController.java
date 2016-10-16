package railroad.controller;

import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import railroad.Main;
import railroad.persistence.dao.StatisticsDao;
import railroad.persistence.entity.Statistics;

import java.util.List;

/**
 * Created by vbuevich on 07.10.2016.
 */
@RestController
public class StatisticsController {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    @RequestMapping("/statistics")
    public @ResponseBody
    List<Statistics> statistics(@RequestParam(value="startTime", defaultValue="") String startTime,
                                @RequestParam(value="endTime", defaultValue="") String endTime) {

        List<Statistics> statistics = StatisticsDao.getStatistics(startTime, endTime);
        return statistics;
    }
}
