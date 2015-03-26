package com.online.exam.dao;

import com.online.exam.entity.StartExam;
import com.online.exam.entity.StudentAnswer;

public interface ExamDao {
	
	public void getExamDetails(String studentId, String subid);
	
	public void startExam(StartExam startExam);
	
	public void recordAnswer(StudentAnswer studentAnswer);
	
	public void startExam(String studentId, String subid);
	
	public long getStudentExamDetails(String studentId, String subid);

}
