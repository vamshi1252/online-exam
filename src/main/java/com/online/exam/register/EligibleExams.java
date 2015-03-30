package com.online.exam.register;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.online.exam.entity.EligibleExamList;
import com.online.exam.entity.EligibleExamsHelper;
import com.online.exam.entity.Exam;
import com.online.exam.entity.StartExam;

public class EligibleExams extends HttpServlet {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/json";

	private static Map<String, Integer> reverseMap = new HashMap<String, Integer>();
	static {
		reverseMap.put("CSE", 1);
		reverseMap.put("EC", 2);
		reverseMap.put("EE", 3);
		reverseMap.put("CI", 4);
		reverseMap.put("ME", 5);
		reverseMap.put("CS", 1);
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String branch = req.getParameter("branch");

		final PrintWriter writer = resp.getWriter();
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		resp.setContentType(CONTENT_TYPE);

		try {
			if (StringUtils.isEmpty(branch)) {
				log("branch is null");
				resp.setStatus(401);
				writer.println("Error: Unauthorized... login again");
			} else {
				HttpSession hs = req.getSession(true);
				if (hs.getAttribute("loginId") == null) {
					resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
					writer.println("Error: SessionExpired");
				} else {
					int branchId = reverseMap.get(branch);
					String examDetails = getEligibleExam(
							(String) hs.getAttribute("loginId"), branchId);
					if (examDetails == null) {
						log("Invalid branch");
						String reponse = "Success : Empty List ";
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

	private String getEligibleExam(String studentId, int branch) {

		List<Exam> examList = ExamDaoImpl.getExamDetailsById(branch);
		List<StartExam> startExamList = ExamDaoImpl
				.getStudentExamDetailsById(studentId);

		Map<String, Double> examTaken = new HashMap<String, Double>();
		for (StartExam startExam : startExamList) {
			String subid = startExam.getSubject();
			Double marks = startExam.getMarks();
			examTaken.put(subid, marks);
		}

		int totalExams = examList.size();
		EligibleExamsHelper[] eligibleExams = new EligibleExamsHelper[totalExams];
		int i = 0;
		for (Exam exam : examList) {
			eligibleExams[i] = new EligibleExamsHelper();

			eligibleExams[i].setSubject(exam.getSubject());
			eligibleExams[i].setSubjectId(exam.getSubid());
			eligibleExams[i].setNoOfQuestions(exam.getNoOfQuestions());
			eligibleExams[i].setTimeInMinutes(exam.getTimeMinutes());
			eligibleExams[i].setTotalMarks(exam.getMarks());

			if (examTaken.containsKey(exam.getSubid())) {
				eligibleExams[i].setStatus(3); // 1 = not active, 2 =
												// Active(TakeExam) 3 = Taken 4
												// = expired
				eligibleExams[i].setMarksScored(examTaken.get(exam.getSubid()));
			} else {
				eligibleExams[i].setStatus(exam.getStatus());
			}
			i++;
		}

		EligibleExamList eligibleExamList = new EligibleExamList();
		eligibleExamList.setEligibleExams(eligibleExams);

		String examDetails = convertToJson(eligibleExamList);
		return examDetails;
	}

	private String convertToJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(obj);
	}

}
