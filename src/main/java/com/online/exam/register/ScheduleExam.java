package com.online.exam.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inmobi.online.dto.Branch;
import com.inmobi.online.dto.Student;
import com.online.exam.dao.impl.ExamDaoImpl;
import com.online.exam.dbconnect.PostgreSQLJDBC;
import com.online.exam.entity.Exam;
import com.online.exam.entity.StartExam;
import com.online.exam.entity.StudentAnswer;
import com.online.exam.entity.TestConnection;
import com.online.exam.response.Response;

public class ScheduleExam extends HttpServlet {

	/**
	 * 
	 */

	private String studentInfo = "SELECT * from Registration where id = %s and password = %s";

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/json";
	private static final String CONTENT = "pong";
	private List<String> responses = new ArrayList<String>();

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
				writer.println("Error: loginId or subid empty");
			} else {
				HttpSession hs = req.getSession(true);
				// if(hs.getAttribute("loginId") == null) {
				// writer.println("Error: SessionExpired");
				// } else {

				Exam  exam = startExam(loginId, subjectId);
				if (exam == null) {
					log("Invalid username || password ");
					String reponse = createResponse("401", null);
					writer.println(reponse);
				} else {
					String reponse = createResponse("200", exam);
					writer.println(reponse);
				}
				// }
			}
		} finally {
			writer.close();
		}
	}

	private Exam startExam(String sid, String subid) {

		StartExam startExam = new StartExam();
		startExam.setSid(sid);
		startExam.setSubject(subid);
		startExam.setStatus(1);
		int result = ExamDaoImpl.startExam(startExam);

		if (result == 1) {
			// get ExamDetails
			Exam exam = ExamDaoImpl.getExamDetails(sid, subid);
			return exam;
		}

		return null;
	}

	private String  convertToJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	private String createResponse(String status, Object result) {
		Response response = new Response();
		response.setResult(result);
		response.setStatus(status);
	
		return convertToJson(response);
	}

}
