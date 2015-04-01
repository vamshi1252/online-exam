package com.online.exam.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.exam.dao.impl.ExamDaoImpl;
import com.online.exam.entity.Answer;
import com.online.exam.entity.AnswerList;
import com.online.exam.entity.QuestionAnswer;
import com.online.exam.entity.QuestionAnswerList;
import com.online.exam.entity.StartExam;

/*
 * @author vamshi vijay
 */

public class StopExam extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html";

	Connection conn = null;
	Statement stmt = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String subjectId = req.getParameter("subid");

		final PrintWriter writer = resp.getWriter();
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		resp.setContentType(CONTENT_TYPE);

		/*
		 * testing hibernate
		 */

		try {
			if (StringUtils.isEmpty(subjectId)) {
				log("username || subid is null");
				resp.setStatus(401);
				writer.println("Error: Unauthorized... login again");
			} else {
				HttpSession hs = req.getSession(true);
				if (hs.getAttribute("loginId") == null) {
					writer.println("Error: SessionExpired");
				} else {
					String result = stopExam(
							(String) hs.getAttribute("loginId"), subjectId);
					// if (result == 1) {
					String response = constructTopHtml() + result + constructBottomHtml();
					writer.println(response);
					// }
				}
			}
		} finally {
			writer.close();
		}
	}

	private String stopExam(String sid, String subid) {
		StartExam startExam = ExamDaoImpl.stopExam(sid, subid);
		String studentAnswers = startExam.getAnswers();
		String options = getAnswers(subid);
		try {
			return computeMarks(options, studentAnswers);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getAnswers(String subid) {
		Answer[] answers = new Answer[15];
		for (int i = 0; i < 15; i++) {
			answers[i] = new Answer();
			answers[i].setAnswer("A");
			answers[i].setMarks(2);
			answers[i].setNegativeMarks(0.667);
			answers[i].setQid(i + 1);
			answers[i].setSubId("TEST");
			answers[i].setType(1);
		}

		AnswerList answerList = new AnswerList();
		answerList.setAnswerList(answers);

		return convertToJson(answerList);
	}

	private String computeMarks(String options, String studentAnswers)
			throws Exception {
		int answeredCounter = 0;
		int notAnsweredCounter = 0;
		int notVisitedCounter = 0;
		int reviewCounter = 0;
		
		 studentAnswers = "{\"questions\":" + studentAnswers +"}" ;

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		AnswerList answers = gson.fromJson(options, AnswerList.class);
		Answer[] answer = answers.getAnswerList();

		QuestionAnswerList qa = gson.fromJson(studentAnswers,
				QuestionAnswerList.class);
		QuestionAnswer[] qAnswer = qa.getQuestions();

		if (answer.length != qAnswer.length) {
			Log.error("questions and Answers not matching");
			throw new Exception("Invalid Answers");
		}

		double marks = 0.0;
		float correct=0.0f; float incorrect =0.0f;
		
		StringBuilder tableString = new StringBuilder();
		
		for (int i = 0; i < answer.length; i++) {

			tableString.append("<tr>");
			tableString.append("<td>" + qAnswer[i].getId() + "</td>");
			tableString.append("<td>" + answer[i].getAnswer() + "</td>");
			
			String status = qAnswer[i].getStatus();

			if ("answered".equalsIgnoreCase(status)) {
				
				tableString.append("<td>" + qAnswer[i].getOption() + "</td>");

				if (qAnswer[i].getOption().equalsIgnoreCase(answer[i].getAnswer())) {
					correct++;
					tableString.append("<td><img src = \"http://leadonlinetestseries.com/images/correct.jpeg\" width=30 height=30> </td>");
					marks = marks + answer[i].getMarks();
					tableString.append("<td><font color=green><b> " + answer[i].getMarks() + "</b></font></td>");
				} else {
					incorrect++;
					marks = marks - answer[i].getNegativeMarks();
					tableString.append("<td><img src = \"http://leadonlinetestseries.com/images/wrong.png\" width=30 height=30></td>");
					tableString.append("<td><font color=red><b> -" + answer[i].getNegativeMarks() + "</b></font></td>");
				}

				answeredCounter++;

			}
			if ("not_answered".equalsIgnoreCase(status)) {
				tableString.append("<td> -- </td>");
				tableString.append("<td> -- </td>");
				tableString.append("<td>0.0</td>");
				notAnsweredCounter++;
			}
			if ("not_visited".equalsIgnoreCase(status)) {
				tableString.append("<td> -- </td>");
				tableString.append("<td> -- </td>");
				tableString.append("<td>0.0</td>");
				notVisitedCounter++;
			}
			if ("review".equalsIgnoreCase(status)) {
				tableString.append("<td> -- </td>");
				tableString.append("<td> -- </td>");
				tableString.append("<td>0.0</td>");
				reviewCounter++;
			}

			// //Segregate Categoires based on status
			// if(categoriesMap.containsKey(qAnswer.getStatus())) {
			// int statusCount = categoriesMap.get(qAnswer.getStatus());
			// categoriesMap.put(qAnswer.getStatus(), statusCount + 1);
			// } else {
			// categoriesMap.put(qAnswer.getStatus(), 1);
			// }
			//
			// // calculate marks
			// if()
			//
			tableString.append("</tr>");
		}
		tableString.append("<tr><td colspan=2 align=right><b>Accuracy = " + correct/answeredCounter +  "</b></td>");
		tableString.append("<td colspan=3 align=right><b>Marks Scored = " + marks +  "</b></td></tr>");

		return tableString.toString();
	}

	private String convertToJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		return gson.toJson(obj);
	}

	private String constructTopHtml() {
		return "<html><title>Result Analysis</title><head><style>table, th, td {border: 4px solid black;border-collapse: collapse;}th, td {padding: 10px;}</style></head><body bgcolor=#CDC8B1><table><tr><td><table style=\"width:100%\" ><tr><th>Questiion No</th><th>Correct Answer</th><th>Your Answer</th><th> Result </th><th> Marks </th></tr>";
	}
	
	private String constructBottomHtml() {
		return "</table></td><td><img src =\"http://localhost:8080/onlineexam/chart\" /></td></tr></table></body></html>";
	}
}
