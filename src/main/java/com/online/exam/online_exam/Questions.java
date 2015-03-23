package com.online.exam.online_exam;

import java.util.ArrayList;
import java.util.List;

public class Questions {
	
	private static Questions qs = new Questions();
	private  List<String> st = new ArrayList<String>();
	
	Questions() {
		st.add("question1");
		st.add("question2");
		st.add("question3");
	}

	public static Questions getInstance() {
		return qs;
	}
	
    public  String getQuestion(String i) {
    	return st.get(Integer.parseInt(i))  ; 
    }

}
