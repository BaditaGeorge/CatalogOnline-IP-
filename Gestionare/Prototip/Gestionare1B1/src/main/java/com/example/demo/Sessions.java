package com.example.demo;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Clasa contine functii ce verifica corectitudinea datelor introduse de utilizator la login(respectand un anumit pattern matching)
 */
public class Sessions {
    //private Verify verify = new Verify();
    //private HelpFunctions funct = new HelpFunctions();
    private SQL_func db = new SQL_func();

    public Date stringToDate(String stringDate){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String currentTime() { //conversia date->string
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format;
        Date date;
        Date current;
        try {
            date = sd.parse("1999-09-02 10:22");
            current = new Date();
            format = sd.format(current);
            return format;
        } catch (Exception e) {
            return "error" + e;
        }
    }
    public String getUsername(String ses_id)
    {
        return db.getUsername(ses_id);
    }
    
    public long hoursPassedSince(Date lastActivity){
        Date current=new Date();
        long diffMiliseconds=Math.abs(current.getTime()-lastActivity.getTime());
        long diffHours = TimeUnit.HOURS.convert(diffMiliseconds, TimeUnit.MILLISECONDS);
        return diffHours;
    }
    public boolean checkSession(String hashedSessionIdParam){

        if(db.countSession(hashedSessionIdParam)!=0){
            if(hoursPassedSince(stringToDate(db.getTime(hashedSessionIdParam)))<2){
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date currentDate=new Date();
                //conversia date->string
                String currentDateString=sd.format(currentDate);
                db.updateSessionActivity(hashedSessionIdParam,currentDateString);
                return true;
            }else{
                db.deleteSession(hashedSessionIdParam);
                return false;
            }
        }else return false;
    }

}