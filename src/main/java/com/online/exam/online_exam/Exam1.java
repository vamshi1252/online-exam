package com.online.exam.online_exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Exam1 extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html";
    private static final String CONTENT = "pong";
    private List<String> responses = new ArrayList<String>();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
    	String output = "NULL Buddy";
    	String input = req.getParameter("qs");
    	Questions qt = Questions.getInstance();
    	if(!("display".equalsIgnoreCase(input))){
    	 output = qt.getQuestion(input);
    	responses.add(input);
    	}
    	
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
        resp.setContentType(CONTENT_TYPE);
        final PrintWriter writer = resp.getWriter();
        try {
        	if("display".equalsIgnoreCase(input)) {
        		writer.println(responses);
        	} else
            writer.println("<html><title>HI</title><body><marquee> " + output + "</marquee><img src =/Users/vamshi.vijay/Downloads/></body>");
        } finally {
            writer.close();
        }
    }
}
