package com.online.exam.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Data;


@Data
public class QuestionAnswer {
	
	public int id;
	public String status;
	public String options;
	
	public QuestionAnswer() {
		this.status = "not_visited";
		this.options = "undefined";
	}
	
}	


//public class QuestionAnswer {
//	
//	
//	public static void main(String args[]) {
//		int n = 15;
//		
//		QuestionAnswe[] qa = new QuestionAnswe[n];
//		
//		for(int i =0; i< n; i++) {
//			qa[i] = new QuestionAnswe();
// 			qa[i].id = i+1;
//		}
//		
//		System.out.println(getJson(qa));
//	}
//
//	
//	public static String getJson(QuestionAnswe[] qa) {
//		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
//		return gson.toJson(qa);
//		
//	}
//}


	
