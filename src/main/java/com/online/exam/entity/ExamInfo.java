package com.online.exam.entity;

import lombok.Data;

@Data
public class ExamInfo {
	public String year = "16";
	public String branch = "CS";
	public String subject = "OS";
	public String username = "Vamshi";
	public ExamName qtype;
	public long totime = 3600000; //ask ask questions
	public long start_time = 1427522400000L;
	public long curr_time = 1427523000000L;
	
	public void setQtype(String branch, String subid) {
		qtype = new ExamName("Computer Science" , subid);
	}

}

class ExamName {
	public String name = "Computer Science";
	public String code = "OS";
	
	public ExamName(String branch, String subid) {
		this.name = branch;
		this.code = subid;
	}
}
