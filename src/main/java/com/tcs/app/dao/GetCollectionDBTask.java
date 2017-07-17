package com.tcs.app.dao;

import java.net.URL;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.tcs.app.CloudantConfig;
import com.tcs.app.entity.Customer;
import com.tcs.app.services.tasks.InsertCustomerTask;

public class GetCollectionDBTask{

    @Resource
	private CloudantConfig dbconfig;

    public Database process(String collectionName){
      Database databaseName=null ;
      try {

        CloudantClient client = ClientBuilder.url(new URL(dbconfig.getHost()))
                    .username(dbconfig.getUsername())
                    .password(dbconfig.getPassword())
                    .build();

          //Get customer db
          // Get a Database instance to interact with, but don't create it if it doesn't already exist
          databaseName = client.database(collectionName, true);
          System.out.println(databaseName.info().toString());
        
      }catch (Exception e)
      {
        e.printStackTrace();
      }

    return databaseName;
  }


}
