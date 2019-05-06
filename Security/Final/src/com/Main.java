package com;

import com.security.HelpFunctions;
import com.security.Register;
import com.security.User;

import javax.mail.MessagingException;
import java.nio.charset.Charset;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) throws MessagingException {
        User user1=new User("rares.darabana","BicpaiEa12","rares.darabana@info.uaic.ro");
        User user2=new User("cristian.gatu","BicpaiEa12","cristian.gatu@info.uaic.ro");

        User user3=new User("danielmihai","dsads%&$#RQWEDS","daniel.mihai@info.uaic.ro");
        System.out.println(user1.register());
        System.out.println(user2.register());
        System.out.println(user3.register());
//        user1.activate("a1a909e12cf45811002dd97f9bec14a2");
//        System.out.println(user1.login());
//        HelpFunctions func=new HelpFunctions();
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        System.out.println(salt);
//        System.out.println(func.byteToString(salt));
//        System.out.println(salt == func.byteToString(salt).getBytes());

    }
}
