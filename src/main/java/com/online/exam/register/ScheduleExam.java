package com.online.exam.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.exam.dao.impl.ExamDaoImpl;
import com.online.exam.entity.Exam;
import com.online.exam.entity.QuestionAnswer;
import com.online.exam.entity.QuestionAnswerList;
import com.online.exam.entity.StartExam;
import com.online.exam.response.AnswersResponse;

public class ScheduleExam extends HttpServlet {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/json";

	Connection conn = null;
	Statement stmt = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String loginId = req.getParameter("sid");
		String subjectId = req.getParameter("subid");

		final PrintWriter writer = resp.getWriter();
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		resp.setContentType(CONTENT_TYPE);

		/*
		 * testing hibernate
		 */

		try {
			if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(subjectId)) {
				log("username || subid is null");
				resp.setStatus(401);
				writer.println("Error: Unauthorized... login again");
			} else {
				HttpSession hs = req.getSession(true);
				// if(hs.getAttribute("loginId") == null) {
				// writer.println("Error: SessionExpired");
				// } else {

				String answers = startExam(loginId, subjectId);
				if (answers == null) {
					log("Invalid username || password ");
					String reponse = createResponse("");
					writer.println(reponse);
				} else {
					String reponse = answers;
					writer.println(reponse);
				}
				// }
			}
		} finally {
			writer.close();
		}
	}

	private String startExam(String sid, String subid) {
		StartExam startExam;

		startExam = ExamDaoImpl.getStudentExamDetails(sid, subid);
		if (startExam != null) {
			return startExam.getAnswers();
		}

		startExam = new StartExam();
		startExam.setSid(sid);
		startExam.setSubject(subid);
		startExam.setStatus(3); // Exam in progress

		Exam exam = ExamDaoImpl.getExamDetails(subid);
		String answers = getAnswersJson(exam.getNoOfQuestions());

		startExam.setAnswers(answers);

		int result = ExamDaoImpl.startExam(startExam);

		return answers;
	}

	private String convertToJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(obj);
	}

	private String createResponse(String status) {
		AnswersResponse response = new AnswersResponse();
		response.setQuestions(status);

		return convertToJson(response);
	}

	private String getAnswersJson(int n) {
		QuestionAnswer[] qa = new QuestionAnswer[n];

		for (int i = 0; i < n; i++) {
			qa[i] = new QuestionAnswer();
			qa[i].id = i + 1;
		}

		QuestionAnswerList questionAnswerList = new QuestionAnswerList();
		questionAnswerList.setQuestions(qa);
		return convertToJson(questionAnswerList);
	}

}
