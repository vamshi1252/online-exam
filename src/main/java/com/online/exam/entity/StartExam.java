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
@Table(name = "student")
public class StartExam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String sid;

	@Column(name = "institute")
	private String institue;

	@Id
	@Column(name = "subject")
	private String subject;

	@Column(name = "marks")
	private double  marks;
	
	@Column(name = "rank")
	private  int  rank;
	
	@Column(name = "time_started")
	private  long  timeStarted;
	
	@Column(name = "time_end_")
	private  long  timeEnd;
	
	@Column(name = "status")
	private  int  status;
	
	@Column(name = "answers")
	private  String  answers;
	
}

