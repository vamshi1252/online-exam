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

import com.inmobi.online.dto.Branch;
import com.inmobi.online.dto.Student;
import com.online.exam.dbconnect.PostgreSQLJDBC;

public class Register extends HttpServlet {

	/**
	 * 
	 */

	private String studentInfo = "SELECT * from Registration where id = %s and password = %s";

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html";
	private static final String CONTENT = "pong";
	private List<String> responses = new ArrayList<String>();

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
				writer.println("Error: loginId or PassWord empty");
			} else {
				HttpSession hs = req.getSession(true);
				if(hs.getAttribute("loginId") == null) {
					writer.println("Info: New Session");
				} else {
					writer.println("Hi " + hs.getAttribute("loginId"));
				}
					
				
				String studentInfo = getStudentDetails(loginId, passWord);
				if (StringUtils.isEmpty(studentInfo)) {
					log("Invalid username || password ");
					writer.println("Error : Invalid UserName or Password");
				} else {
					writer.println(studentInfo);
					hs.setAttribute("loginId", loginId);
				}
			}
		} finally {
			writer.close();
		}
	}

	private String getStudentDetails(String loginId, String passWord) {

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
			
			return convertToJson(student);

		} catch (ClassNotFoundException e) {
			return "Error : ClassNotFoundException";
		} catch (SQLException e) {
			return "Error : SQLException" + e;
		}
	}

	private String convertToJson(Student student) {
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String json;
		try {
			json = ow.writeValueAsString(student);
		} catch (JsonGenerationException e) {
			return "Error : JsonGenerationException";
		} catch (JsonMappingException e) {
			return "Error : JsonGenerationException";
		} catch (IOException e) {
			return "Error :JsonGenerationException";
		}
		return json;
	}

}
