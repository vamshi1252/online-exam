package com.online.exam.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC {

	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://", "vamshi",
				"vamshi");
		
//		Connection conn = DriverManager.getConnection(
//				"jdbc:postgresql://localhost:5432/gateonline", "postgres",
//				"postgres");
		return conn;

	}
}
