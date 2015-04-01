package com.online.exam.dao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("image/png");

		OutputStream outputStream = response.getOutputStream();

		JFreeChart chart = getChart();
		int width = 500;
		int height = 500;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

	public JFreeChart getChart() {
//		DefaultPieDataset dataset = new DefaultPieDataset();
//		dataset.setValue("Ford", 23.3);
//		dataset.setValue("Chevy", 32.4);
//		dataset.setValue("Yugo", 44.2);
//
//		boolean legend = true;
//		boolean tooltips = false;
//		boolean urls = false;
//
//		JFreeChart chart = ChartFactory.createPieChart("Cars", dataset, legend, tooltips, urls);
//
//		chart.setBorderPaint(Color.GREEN);
//		chart.setBorderStroke(new BasicStroke(5.0f));
//		chart.setBorderVisible(true);
		
		 final String answered = "Answered";
	      final String notAnswered = "NotAnswered";
	      final String markedForReview = "MarkedForReview";
	      final String notVisited = "NotVisited";

	      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

	      dataset.addValue( 8 , answered , "Exam Analysis" );
	      dataset.addValue( 17, notAnswered , "Exam Analysis" );
	      dataset.addValue( 4 , markedForReview , "Exam Analysis" );
	      dataset.addValue( 4 , notVisited , "Exam Analysis" );


	      JFreeChart barChart = ChartFactory.createBarChart(
	         "Answering Statistics", 
	         "Category", "Questions", 
	         dataset,PlotOrientation.VERTICAL, 
	         true, true, false);
	      
	      barChart.setBorderPaint(Color.GREEN);
	      barChart.setBorderStroke(new BasicStroke(5.0f));
	      barChart.setBorderVisible(true);
	      
		return barChart;
	}

}
