package com.basinda.utils;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.springframework.util.MultiValueMap;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SendOTPUtils {
    private final static String AUTH_TOKEN = "13a6aadf232c1b77ade2005d36e9213f";
    private final static String ACCOUNT_STD = "AC2130425c246df406ac330146712e06ba";
    private final static String FROM_NUMBER = "+8801315656967";

    public static void send(String phone) throws ParseException {
        Twilio.init(ACCOUNT_STD, AUTH_TOKEN);
        int min = 1000;
        int max = 9999;
        int number = (int)(Math.random()*(max-min+1)+min);

        String msg = "Your OTP = "+number+" please verify this OTP in your Application by Teamwebsoft.com";
        Message message = Message.creator(new PhoneNumber("+88"+phone),new PhoneNumber(FROM_NUMBER), msg).create();

        Date myDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = simpleDateFormat.parse(myDate.toString());
        long millis = date.getTime();
    }

    public void receive(MultiValueMap<String, String> smsCallBack){

    }
}