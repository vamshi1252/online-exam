package com.online.exam.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.online.exam.dao.ExamDao;
import com.online.exam.dbconnect.HibernateSessionFactory;
import com.online.exam.entity.Exam;
import com.online.exam.entity.StartExam;
import com.online.exam.entity.StudentAnswer;

public  class ExamDaoImpl /*implements ExamDao */{

//	@Override
	public  static int startExam(StartExam startExam) {
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			if (StringUtils.isBlank(startExam.getInstitue())) {
				startExam.setInstitue("LEAD");
			}
			
			startExam.setTimeStarted(System.currentTimeMillis());

			session.save(startExam);
			tx.commit();
			
			return 1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return 0;
	}
	
	
	public  static int updateAnswer(String  sid, String subid, String answers) {
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			
			StartExam startExam = getStudentExamDetails(sid, subid);
			startExam.setAnswers(answers);
			tx = session.beginTransaction();
			
			session.update(startExam);
			tx.commit();
			
			return 1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return 0;
	}
	
	
	public  static int stopExam(String  sid, String subid) {
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			
			
			
			StartExam startExam = getStudentExamDetails(sid, subid);
			startExam.setStatus(2);
			tx = session.beginTransaction();
			
			session.update(startExam);
			tx.commit();
			
			return 1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return 0;
	}
	
	
//
////	@Override
//	public static void recordAnswer(StudentAnswer studentAnswer) {
//		Session session = HibernateSessionFactory.getInstance().getSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//
//			Criteria criteria = session.createCriteria(StudentAnswer.class);
//			criteria.add(Restrictions.eq("sid", "1225"));
//			StudentAnswer sa = (StudentAnswer) criteria.uniqueResult();
//			System.out.println(sa);
//			tx.commit();
//		} catch (RuntimeException e) {
//			if (tx != null)
//				tx.rollback();
//			throw e; // or display error message
//		}
//	}

//	@Override
	public static Exam getExamDetails(String subid) {
		
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Exam.class);
			criteria.add(Restrictions.eq("subid", subid));
			Exam exam = (Exam) criteria.uniqueResult();
			System.out.println(exam);
			tx.commit();
			
			return exam;
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e; // or display error message
		}
	}
	
	public static List<Exam> getExamDetailsById(int branchId) {
		
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Exam.class);
			criteria.add(Restrictions.eq("branch", branchId));
			criteria.addOrder(Order.asc("subject"));
			List<Exam> examList =  criteria.list();
			System.out.println(examList);
			tx.commit();
			
			return examList;
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			session.close();
			session = null;
			throw e; // or display error message
		}
	}
	
	
	
	
	

//	@Override
	public static StartExam getStudentExamDetails(String studentId, String subid) {

		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(StartExam.class);
			criteria.add(Restrictions.eq("sid", studentId));
			criteria.add(Restrictions.eq("subject", subid));

			StartExam sa = (StartExam) criteria.uniqueResult();
//			System.out.println(sa);
			tx.commit();
			return sa;
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			session.close();
			session = null;
			throw e; // or display error message
		}
	}
	
	
//	@Override
	public static List<StartExam> getStudentExamDetailsById(String studentId) {

		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(StartExam.class);
			criteria.add(Restrictions.eq("sid", studentId));

			List<StartExam> startExamList =  criteria.list();
//			System.out.println(sa);
			tx.commit();
			return startExamList;
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			session.close();
			session = null;
			throw e; // or display error message
		}
	}
	

//	@Override
	public void startExam(String studentId, String subid) {
		// TODO Auto-generated method stub
		
	}

}
