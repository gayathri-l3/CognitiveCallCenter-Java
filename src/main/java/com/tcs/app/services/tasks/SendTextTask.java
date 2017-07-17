package com.tcs.app.services.tasks;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendTextTask {
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC8509b48c73b6c14c71b2c4f1ad82180c";
  public static final String AUTH_TOKEN = "887b3dc7e771a153004827d5d8ead0b9";

  public static void process(String targetNumber, String messageContent) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    
    Message message = Message.creator(new PhoneNumber("+1" + targetNumber),
        new PhoneNumber("+15134333559"), 
        messageContent).create();

    System.out.println(message.getSid());
  }
}
