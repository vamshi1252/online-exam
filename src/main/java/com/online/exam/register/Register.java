package com.online.exam.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inmobi.online.dto.Branch;
import com.inmobi.online.dto.Student;
import com.online.exam.dbconnect.PostgreSQLJDBC;
import com.online.exam.response.Response;

public class Register extends HttpServlet {

	/**
	 * 
	 */

	private String studentInfo = "SELECT * from Registration where id = %s and password = %s";

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/json";
	
	private static GsonBuilder builder = new GsonBuilder();
	
	Connection conn = null;
	Statement stmt = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String loginId = req.getParameter("id");
		String passWord = req.getParameter("pwd");
		
		final PrintWriter writer = resp.getWriter();
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		resp.setContentType(CONTENT_TYPE);

		try {
			if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(passWord)) {
				log("username || password is null");
				String reponse = createResponse("401", null);
				writer.println(reponse);
			} else {
				HttpSession hs = req.getSession(true);
				
				Student student = getStudentDetails(loginId, passWord);
				if (student == null) {
					log("Invalid username || password ");
					String reponse = createResponse("401", null);
					writer.println(reponse);
				} else {
					String reponse = createResponse("200", student);
					writer.println(reponse);
					hs.setAttribute("loginId", loginId);
				}
			}
		} finally {
			writer.close();
		}
	}

	private Student getStudentDetails(String loginId, String passWord) {

		Student student = new Student();
		try {
			conn = PostgreSQLJDBC.getConnection();
			stmt = conn.createStatement();
			String query = String.format(studentInfo, "'" + loginId + "'", "'"
					+ passWord + "'");
			ResultSet rs = stmt.executeQuery(query);

			if (rs == null)
				return null;

			while (rs.next()) {
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setInstitue(rs.getString("institute"));
				student.setEmailId(rs.getString("emailid"));
				student.setBranch(Branch.findByValue(rs.getInt("branch")));
				student.setMobile(rs.getString("mobile"));
			}

			stmt.close();
			conn.close();
			
			return student;

		} catch (ClassNotFoundException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
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
