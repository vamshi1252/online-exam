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

/*
 * @author vamshi vijay
 */

public class SubmitAnswer extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/json";

	Connection conn = null;
	Statement stmt = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String answers = req.getParameter("answers");
		String subjectId = req.getParameter("subid");

		final PrintWriter writer = resp.getWriter();
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		resp.setContentType(CONTENT_TYPE);

		/*
		 * testing hibernate
		 */
		
		try {
			if ( StringUtils.isEmpty(subjectId) || StringUtils.isEmpty(answers)) {
				log("username || subid is null");
				resp.setStatus(401);
				writer.println("Error: Unauthorized... login again");
			} else {
				HttpSession hs = req.getSession(true);
				 if(hs.getAttribute("loginId") == null) {
				 writer.println("Error: SessionExpired");
				 } else {

				int result = submitAnswer((String)hs.getAttribute("loginId"), subjectId, answers);
//				if (result == 1) {
					String response = "Success";
					writer.println(response);
//				}
				 }
			}
		} finally {
			writer.close();
		}
	}

	private int submitAnswer(String sid, String subid, String answers) {

		
		int result = ExamDaoImpl.updateAnswer(sid, subid, answers);
		
		return result;
	}

}
