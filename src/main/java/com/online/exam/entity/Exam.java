package com.online.exam.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "exam")
public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "branch")
	private int branch;

	
	@Column(name = "sid")
	private String subid;
	
	@Id
	@Column(name = "institute")
	private String institue;

	@Id
	@Column(name = "subject")
	private String subject;

	@Column(name = "no_questions")
	private int  noOfQuestions;
	
	@Column(name = "time_minutes")
	private  long  timeMinutes;
	
	@Column(name = "status")
	private  int  status;
	
	@Column(name = "marks")
	private double marks;
}


