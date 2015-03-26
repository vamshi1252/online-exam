package com.online.exam.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

//	@Override
	public static void recordAnswer(StudentAnswer studentAnswer) {
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(StudentAnswer.class);
			criteria.add(Restrictions.eq("sid", "1225"));
			StudentAnswer sa = (StudentAnswer) criteria.uniqueResult();
			System.out.println(sa);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e; // or display error message
		}
	}

//	@Override
	public static Exam getExamDetails(String studentId, String subid) {
		
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Exam.class);
			criteria.add(Restrictions.eq("subid", "OS"));
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

//	@Override
	public static long getStudentExamDetails(String studentId, String subid) {

		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(StartExam.class);
			criteria.add(Restrictions.eq("id", studentId));
			criteria.add(Restrictions.eq("subject", subid));

			StartExam sa = (StartExam) criteria.uniqueResult();
			System.out.println(sa);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e; // or display error message
		}

		return 0;

	}

//	@Override
	public void startExam(String studentId, String subid) {
		// TODO Auto-generated method stub
		
	}

}
