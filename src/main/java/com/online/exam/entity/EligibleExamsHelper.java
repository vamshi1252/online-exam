package com.online.exam.entity;

import lombok.Data;


@Data
public class EligibleExamsHelper {
	
	private String subject ="Operating System";
	
	private String subjectId = "OS";
	
	private int status;
	
	private int noOfQuestions;
	
	private long timeInMinutes;
	
	private double totalMarks;
	
	private double marksScored;

}
