package com.online.exam.entity;

import lombok.Data;

@Data
public class ExamInfo {
	public String year = "16";
	public String branch = "CS";
	public String subject = "OS";
	public String username = "Vamshi";
	public ExamName qtype = new ExamName();
	public long totime = 3600000; //ask ask questions
	public long start_time = 1427522400000L;
	public long curr_time = 1427523000000L;

}

class ExamName {
	public String name = "Computer Science";
	public String code = "OS";
}
