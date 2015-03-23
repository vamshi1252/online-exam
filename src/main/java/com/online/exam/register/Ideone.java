package com.online.exam.register;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone {

	public static void main(String[] args) throws java.lang.Exception {
		BufferedReader rd;
		try {
			String urlString = "http://api.playstoreapi.com/v1.1/apps/com.whatsapp?key=739d14584dfd2355f10ce6fd494162a9";
			String line ="";
			String result = "";
			URL url = new URL(urlString);
			
			long startTime = System.currentTimeMillis();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			long endTime = System.currentTimeMillis();

			System.out.println("That took " + (endTime - startTime) + " milliseconds");
			
			
			while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	         System.out.println(result);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}