package com.online.exam.entity;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "studentanswer")
public class StudentAnswer {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "sid")
	private String sid;

	@Column(name = "subid")
	private String subid;

	@Column(name = "answer")
	private String answer;

	@Column(name = "status")
	private int status;
}
