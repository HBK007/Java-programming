/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Duc Hai
 */
public class Util {
    public static String convertTime1(String time){
        String strDate = "";
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(time);
            strDate = outFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strDate;
    }
    public static String convertTime2(String time){
        String strDate = "";
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat outFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date date = inputFormat.parse(time);
            strDate = outFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strDate;
    }
    public static String convertTime3(String time){
        String strDate = "";
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(time);
            strDate = outFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strDate;
    }
    public static Date convertStringToDate(String datetime)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH24:MI");
        try{
            Date date = simpleDateFormat.parse(datetime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
