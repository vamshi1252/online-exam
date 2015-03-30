package com.online.exam.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.online.exam.entity.ExamInfo;
import com.online.exam.entity.QuestionAnswer;
import com.online.exam.entity.QuestionAnswerList;
import com.online.exam.entity.StartExam;
import com.online.exam.response.AnswersResponse;

public class ExamDetails extends HttpServlet {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/json";
	
	
	private static Map<Integer, String> mp = new HashMap<Integer, String>();
	static	{
	mp.put(1, "CS");
	mp.put(2, "EC");
	mp.put(3, "EE");
	mp.put(4, "CI");
	mp.put(5, "ME");
	}

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
				log("subid is null");
				resp.setStatus(401);
				writer.println("Error: Unauthorized... login again");
			} else {
				HttpSession hs = req.getSession(true);
				 if(hs.getAttribute("loginId") == null) {
				 writer.println("Error: SessionExpired");
				 } else {

				String examDetails = getExamDetails((String)hs.getAttribute("loginId"),subjectId);
				if (examDetails == null) {
					log("Invalid subid");
					String reponse = createResponse("");
					writer.println(reponse);
					resp.setStatus(401);
				} else {
					String reponse = examDetails;
					writer.println(reponse);
				}
				 }
			}
		} finally {
			writer.close();
		}
	}

	private String getExamDetails(String studentId, String subid) {

		Exam exam = ExamDaoImpl.getExamDetails(subid);
		StartExam startExam = ExamDaoImpl.getStudentExamDetails(studentId, subid);
//		exam.setMarks(30);
		ExamInfo examInfo = new ExamInfo();
		examInfo.setBranch(mp.get(exam.getBranch()));
		examInfo.setSubject(subid);
		examInfo.setUsername(studentId);
		examInfo.setQtype(mp.get(exam.getBranch()), subid);
		if(startExam ==  null) {
			examInfo.setStart_time(System.currentTimeMillis());
		} else {
		examInfo.setStart_time(startExam.getTimeStarted());
		}
		examInfo.setCurr_time(System.currentTimeMillis());
		examInfo.setTotime(exam.getTimeMinutes()*60*1000);

		String examDetails = convertToJson(examInfo);
		return examDetails;
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

}
