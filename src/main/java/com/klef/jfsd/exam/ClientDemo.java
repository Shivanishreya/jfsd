package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Insert records
            Project project1 = new Project();
            Project project2 = new Project();
            Project project3 = new Project();

            session.save(project1);
            session.save(project2);
            session.save(project3);

            // Aggregate functions using Criteria
            Criteria criteria = session.createCriteria(Project.class);

            // Count
            criteria.setProjection(Projections.rowCount());
            System.out.println("Total Projects: " + criteria.uniqueResult());

            // Max Budget
            criteria.setProjection(Projections.max("budget"));
            System.out.println("Maximum Budget: " + criteria.uniqueResult());

            // Min Budget
            criteria.setProjection(Projections.min("budget"));
            System.out.println("Minimum Budget: " + criteria.uniqueResult());

            // Sum Budget
            criteria.setProjection(Projections.sum("budget"));
            System.out.println("Total Budget: " + criteria.uniqueResult());

            // Average Budget
            criteria.setProjection(Projections.avg("budget"));
            System.out.println("Average Budget: " + criteria.uniqueResult());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
