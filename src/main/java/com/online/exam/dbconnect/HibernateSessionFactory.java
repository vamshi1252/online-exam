package com.online.exam.dbconnect;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateSessionFactory {

	private static HibernateSessionFactory hbs ;
	
	private  SessionFactory sessionFactory;

	private  Session session;
	
	private HibernateSessionFactory() {
		
	}

	public  Session getSession() {
		if (session == null) {
			session = getSessionFactory().openSession();
		}
		return session;
	}

	private  SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"META-INF/spring/hibernate.xml");

			sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		}

		return sessionFactory;
	}

	public static HibernateSessionFactory getInstance() {
		if(hbs == null){
			hbs = new HibernateSessionFactory();
		}
		return hbs;
	}
	
}
