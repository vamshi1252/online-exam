package com.online.exam.entity;

import lombok.Data;

@Data
public class Answer {
	
	private double marks;
	
	private double negativeMarks;
	
	private String answer;
	
	private int type; // 0 for multiple choice and 1 for fill in the blank
	
	private String subId;
	
	private int qid;
	
}