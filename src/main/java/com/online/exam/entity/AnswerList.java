package com.online.exam.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Data;

@Data
public class AnswerList {
	private Answer[] answerList;
	
//	private  static String getAnswers(String subid) {
//		Answer[] answers =  new Answer[25];
//		for(int i=0;i<25; i++) {
//			answers[i] = new Answer();
//			answers[i].setAnswer("A");
//			answers[i].setMarks(2);
//			answers[i].setNegativeMarks(0.67);
//			answers[i].setQid(i+1);
//			answers[i].setSubId("TEST");
//			answers[i].setType(1);
//			
//		}
//		
//		AnswerList answerList =  new AnswerList();
//		answerList.setAnswerList(answers);
//		
//		return convertToJson(answerList);
//	}
//	
//	private static  double computeMarks(String options, String studentAnswers) {
//		
//		
//		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
//		AnswerList qa = gson.fromJson(options, AnswerList.class);
//		System.out.println(qa);
//		
//		return 0;
//	}
//	
//	private static  String convertToJson(Object obj) {
//		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
//		
//		return gson.toJson(obj);
//	}
//	public static void main(String args[]) {
//		
//		String options = getAnswers("OS");
//		computeMarks(options, options);
//		
//	}

}



