/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Douiri Amine
 */
public class SMSUtils {
    public static final String ACCOUNT_SID = "ACe5cf5a49c02886c88038ad272734639d";
    public static final String AUTH_TOKEN = "2a5d8333e860ef0dfd3e2b8debd91a92";
    public static void send(int  Content){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String m = "Your activation code is :"+Content;
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+12243728379"),
                new com.twilio.type.PhoneNumber("+15005550006"),
                m)
            .create();

        System.out.println(message.getSid());
    }
}
