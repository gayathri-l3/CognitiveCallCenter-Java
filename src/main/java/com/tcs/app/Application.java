package com.tcs.app;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.tcs.app.common.util.ConvertWordDigitsToNumber;
import com.tcs.app.dao.CustomerDao;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.services.tasks.FetchTask;
import com.tcs.app.services.tasks.InsertCustomerTask;
import com.tcs.app.services.tasks.UpdateTask;

@SpringBootApplication
//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(EnvironmentConfig.class, args);
        System.out.println("Cognitive Call Center Spring Boot App is ready");
    }

    @Resource
	private CloudantConfig dbconfig;

    @Bean
    public Database[] cloudantclient () {

      Database[] dbArr = new Database[2]; //First index is customerbilling (customer info), second index is conversation exchanges

      try {

        CloudantClient client = ClientBuilder.url(new URL(dbconfig.getHost()))
                    .username(dbconfig.getUsername())
                    .password(dbconfig.getPassword())
                    .build();
 
          //Get customer db
          // Get a Database instance to interact with, but don't create it if it doesn't already exist
          dbArr[0] = client.database("customeraccounts", true);
          dbArr[1] = client.database("customerbillingconversations", true);
          dbArr[2]= client.database("customerorders", true);
          System.out.println(dbArr[0].info().toString());
          System.out.println(dbArr[1].info().toString());
          
          
   
          //Leaving this comment block here till we can test the db connection with bluemix working
         /* try {
              Class.forName("org.postgresql.Driver");
          }
          catch (java.lang.ClassNotFoundException e) {
              System.out.println(e.getMessage());
          }
          
          String url = "jdbc:postgresql://jumbo.db.elephantsql.com:5432/rzipbamz";
          String username = "rzipbamz";
          String password = "uqKWZ3QzPteLdIYw2sQTwiSNvv1iymzq";

          try {
              Connection db = DriverManager.getConnection(url, username, password);
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
          catch (java.sql.SQLException e) {
              System.out.println(e.getMessage());
          }
          */
          
                    
      }catch (Exception e)
      {
        e.printStackTrace();
      }

    return dbArr;
  }


}
