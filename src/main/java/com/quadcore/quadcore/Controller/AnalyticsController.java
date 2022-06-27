package com.quadcore.quadcore.Controller;
import com.quadcore.quadcore.Entities.Analytics;
import com.quadcore.quadcore.Entities.GraphResult;
import com.quadcore.quadcore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AnalyticsController {

    @Autowired
    AnalyticsService analyticsService;
    // card_type, sum(amount), 
    @RequestMapping(value = "/analytics-graph/", method = RequestMethod.POST)
    public List<GraphResult> getCardAllocation(@RequestBody Analytics analytic) {
        String sqlQuery="select c.card_type,month(t.transaction_date),sum(t.transaction_amount) from transaction t inner join card_allocation ca on t.card_id = ca.card_id inner join user u on ca.employee_id=u.employee_id inner join card c on c.card_id=ca.card_id where ";
        if (analytic.getDuration() != null) {
            sqlQuery+="year(t.transaction_date)='"+analytic.getDuration().getYear()+"'";
        } else {
            sqlQuery+="year(t.transaction_date)= year(current_date)";
        }
        if (analytic.getCardname() != null) {
            sqlQuery+=" and c.card_type='"+analytic.getCardname()+"'";
        }

        if (analytic.getDepartment() != null) {
            sqlQuery+=" and u.department='"+analytic.getDepartment()+"'";
        }

        if (analytic.getDesignation() != null) {
            sqlQuery+=" and u.designation='"+analytic.getDesignation()+"'";
        }
        sqlQuery+=" group by c.card_type, month(t.transaction_date);";
        return analyticsService.getAnalyticsBase(sqlQuery);
    }
}
