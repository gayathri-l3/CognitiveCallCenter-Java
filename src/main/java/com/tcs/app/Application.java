package com.tcs.app;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.tcs.app.controller.CallCenterRestController;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args){
		
		CallCenterRestController.init("2017-02-03",
				"74a4f528-cefa-47cc-8d12-4ae80fbfcc13",
				"EJuGmkJ8DMXV",
				"05a5344d-1cb6-40bc-9120-04567aa2fddf");
		
		CallCenterRestController.conversationMap = new HashMap<String, Map<String, Object>>();
		SpringApplication.run(EnvironmentConfig.class, args);
		System.out.println("Callcenter app ready");
		
	}
	
	@Resource 
	private CloudantConfig cloudantConfig;
	
	@Bean
	public Database cloudantClient(){
		
		Database dataBase = null;
		System.out.println(cloudantConfig.toString());
		
		try{
			
			System.out.println("----------------------------------------------");
			System.out.println("Host : " + cloudantConfig.getHost());
			System.out.println("Username : " + cloudantConfig.getUsername());
			System.out.println("Password : " + cloudantConfig.getPassword());
			System.out.println("----------------------------------------------");
			
			CloudantClient client = ClientBuilder.url(new URL(cloudantConfig.getHost()))
					.username(cloudantConfig.getUsername())
					.password(cloudantConfig.getPassword())
					.build();
		
			
			dataBase = client.database("customer", true);
			System.out.println("Data base info here --------------------" + dataBase.info().toString());
		} catch(Exception ex){
			System.out.println("In catch block" + ex.getMessage());
		}
		
		return dataBase;
	}
}
