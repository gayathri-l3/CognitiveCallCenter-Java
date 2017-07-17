package com.tcs.app.common.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ElephantSqlConnection {

	public static Connection getConnection(){
	
		try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        String url = "jdbc:postgresql://jumbo.db.elephantsql.com:5432/rzipbamz";
        String username = "rzipbamz";
        String password = "uqKWZ3QzPteLdIYw2sQTwiSNvv1iymzq";
        Connection database = null;
        try {
            database = DriverManager.getConnection(url, username, password);
            /*
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Payment");
            while (rs.next()) {
          	  System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
            }
            rs.close();
            st.close();
            }
           */
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
		return database;
	}
}