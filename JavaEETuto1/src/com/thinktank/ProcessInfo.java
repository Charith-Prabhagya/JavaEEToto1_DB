package com.thinktank;
import java.io.IOException;

import java.sql.*;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet implementation class ProcessInfo
 */
@WebServlet("/ProcessInfo")
public class ProcessInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProcessInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Send all get requests to doPost
		doPost(request, response);
	}
 
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// The URL to send data to (JSP FILE)
		String url = "/DisplayInfo.jsp";
		
		// SIMPLE EXAMPLE
		// Get the data entered on index.jsp
		// String usersName = request.getParameter("name");
		// request.setAttribute("usersName", usersName);
		
		// DATABASE EXAMPLE
		// Get the data entered on index.jsp
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String phone = request.getParameter("phone");
		
		// Update the DB
		updateDB(fName, lName, phone);
		
		// Create object to pass to DisplayInfo.jsp
		Customer cust = new Customer(fName, lName, phone);
		request.setAttribute("cust", cust);
		
		// Forward data to DisplayInfo.jsp
		getServletContext()
			.getRequestDispatcher(url)
			.forward(request, response);
	}
	
	// Setup MySQL Connector
	// Copy mysql-connector-java-8.0.15.jar into 
	// /WebContent/WEB-INF/lib/
	
	/*
	 * SETUP DB
	 * mysql -u root -p
	 * UPDATE mysql.user SET Password=PASSWORD('NEWPW') 
	 * WHERE User='root';
	 * CREATE DATABASE test1;
	 * USE test1;
	 * CREATE TABLE customer(
	 * first_name VARCHAR(30) NOT NULL,
	 * last_name VARCHAR(30) NOT NULL,
	 * phone VARCHAR(20) NOT NULL,
	 * cust_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);
	 * CREATE USER 'dbadmin'@'localhost' IDENTIFIED BY 'turtledove';
	 * GRANT ALL PRIVILEGES ON test1.customer TO 
	 * 'dbadmin'@'localhost' IDENTIFIED BY 'turtledove';
	 */
	
	// Adds users to the DB
		protected void updateDB(String fName, String lName, String phone) {
			// Connects to the DB
			Connection con;
			
			try {
				// Everything needed to connect to the DB
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost/test1";
		        String user = "dbadmin";
		        String pw = "turtledove";
		        
		        // Used to issue queries to the DB
		        con = DriverManager.getConnection(url, user, pw);
		        
		        // Sends queries to the DB for results
		        Statement s = con.createStatement();
		        
		        // Add a new entry
		        String query = "INSERT INTO CUSTOMER " + 
		        "(first_name, last_name, phone, cust_id) " + 
		        "VALUES ('" + fName + "', '" + lName + "', '" +
		        phone + "', NULL)";
		        
		        // Execute the Query
		        s.executeUpdate(query);
		        
		        // Close DB connection
		        con.close();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
 
}
 