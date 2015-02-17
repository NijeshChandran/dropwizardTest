package com.dwbook.phonebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
	            // The newInstance() call is a work around for some
	            // broken Java implementations

	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception ex) {
	            // handle the error
	        }
	        Connection conn = null;
	        try {
	            conn =
	               DriverManager.getConnection("jdbc:mysql://localhost/phonebook?user=phonebookuser&password=phonebookpassword");

	            // Do something with the Connection

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        
	        Statement stmt = null;
	        ResultSet rs = null;

	        try {
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery("SELECT * FROM contact");
	            
	            while(rs.next()){
	            	System.out.println(rs.getInt("id") + " " +  rs.getString("firstName") + " " +  rs.getString("lastName") + " " +  rs.getString("phone"));
	            }
	        }
	        catch (SQLException ex){
	        	  ex.printStackTrace();
	        }
	        finally {

	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException sqlEx) { } // ignore

	                rs = null;
	            }

	            if (stmt != null) {
	                try {
	                    stmt.close();
	                } catch (SQLException sqlEx) { } // ignore

	                stmt = null;
	            }
	        }
	        

	}

}
