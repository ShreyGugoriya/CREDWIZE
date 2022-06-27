package com.quadcore.quadcore.utility;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    static{
        try {
            Configuration cfg = new Configuration();
            Properties p = new Properties();
//load properties file
            p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.cfg.xml"));
            cfg.setProperties(p);
            sessionFactory = cfg.configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutDown(){
        //closes caches and connections
        getSessionFactory().close();
    }
}