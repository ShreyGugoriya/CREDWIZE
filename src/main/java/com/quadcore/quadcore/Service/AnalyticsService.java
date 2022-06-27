package com.quadcore.quadcore.Service;
import com.quadcore.quadcore.Entities.GraphResult;
import com.quadcore.quadcore.Repo.AnalyticsRepository;
import com.quadcore.quadcore.utility.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AnalyticsService {

    @Autowired
    AnalyticsRepository analyticsRepository;
    public List<GraphResult> getAnalyticsBase(String sqlQuery) {
//        System.out.println(sqlQuery);
// Prep work
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory().openSession();
        Session session = HibernateUtil.getSessionFactory().openSession();
//        Session session = sessionFactory.getSessionFactory;
// Get All Employees
        Transaction tx = session.beginTransaction();
        SQLQuery query = session.createSQLQuery(sqlQuery);
        List<GraphResult> rows = (List<GraphResult>) query.list();
//        return analyticsRepo.func(sqlQuery);
        tx.commit();
        return rows;
    }

}