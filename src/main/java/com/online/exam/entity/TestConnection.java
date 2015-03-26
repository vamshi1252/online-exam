package com.online.exam.entity;



import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.exam.dbconnect.HibernateSessionFactory;
import com.online.exam.entity.*;
import com.online.exam.response.Response;



  
public class TestConnection {
	
	
	private  SessionFactory sessionFactory;
	
	public  Session getSession() {
		return sessionFactory.getCurrentSession(); 
	}
	
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



//public StudentAnswer  hiber() {  
//      
//    //creating session object  
//    TestConnection tc = new TestConnection();
//    Session ses = tc.getSession();
//    
//    Criteria criteria = ses.createCriteria(StudentAnswer.class);
//    criteria.add(Restrictions.eq("sid", "1252"));
//    StudentAnswer sa =  (StudentAnswer) criteria.uniqueResult();
//    
//    System.out.println(sa);
//    return sa;
//      
//    //creating transaction object  
//}

public static void main(String args[]) {
//	      ApplicationContext context = 
//	             new ClassPathXmlApplicationContext("META-INF/spring/hibernate.xml");
//
//	      SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
//	      Session session = sessionFactory.openSession();
//	       session = sessionFactory.getCurrentSession();
//	      
//	      
//	      Criteria criteria =    session.createCriteria(StudentAnswer.class);
//	      criteria.add(Restrictions.eq("sid", "1225"));
//	      StudentAnswer sa =  (StudentAnswer) criteria.uniqueResult();
//	      session.close();
//	      System.out.println(sa);
	
//	Session session = HibernateSessionFactory.getSession();
	Session session = HibernateSessionFactory.getInstance().getSession();
	Transaction tx = null;
	try {
	    tx = session.beginTransaction();
	    
//		Criteria criteria = session.createCriteria(StartExam.class);
//		criteria.add(Restrictions.eq("sid", "test1252"));
//		criteria.add(Restrictions.eq("subject", "OS"));
//		
//		StartExam sa = (StartExam) criteria.uniqueResult();
//		System.out.println(sa);
	    
	    Criteria criteria = session.createCriteria(Exam.class);
		criteria.add(Restrictions.eq("subid", "OS"));
		Exam sa = (Exam) criteria.uniqueResult();
		System.out.println(sa);
	    System.out.println("Saved");
	    tx.commit();
	}
	catch (RuntimeException e) {
	    if (tx != null) tx.rollback();
	    throw e; // or display error message
	}

	
	
	      	      
//    Configuration cfg=new Configuration();  
//    cfg.configure("META-INF/spring/hibernate.xml");//populates the data of the configuration file  
//      
//    //creating seession factory object  
//    SessionFactory factory=cfg.buildSessionFactory();  
//      
//    //creating session object  
//    Session session=factory.openSession();  
//      
//    //creating transaction object  
//    Transaction t=session.beginTransaction();  
//          
//    StudentAnswer e1=new StudentAnswer();  
//    e1.setId(115);  
//    e1.setSid("12");
//    e1.setSubid("3456");
//    e1.setAnswer("A");
//    e1.setStatus(2);
//      
//    session.persist(e1);//persisting the object  
//      
//    t.commit();//transaction is committed  
//    session.close();  
//      
    System.out.println("successfully saved");  
	
}

 public void ctoj() {
	 GsonBuilder builder = new GsonBuilder();
     Gson gson = builder.create();
     Response res = new Response();
     res.setResult("adf");
     res.setStatus("200");
     System.out.println(gson.toJson(res));
 }

}