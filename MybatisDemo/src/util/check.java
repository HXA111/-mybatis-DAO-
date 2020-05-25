package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class check {
    public check(){
    }

    public static String md5(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

    public static boolean isPassword(String password){
        Pattern pattern=Pattern.compile("\\d");
        Pattern pattern1=Pattern.compile("[a-z]");
        Pattern pattern2=Pattern.compile("[A-Z]");
        Matcher matcher=pattern.matcher(password);
        Matcher matcher1=pattern1.matcher(password);
        Matcher matcher2=pattern2.matcher(password);
        boolean flag=false;
        if(password.length()>=6&&matcher.find()&&matcher1.find()&&matcher2.find()){
            flag=true;
        }
        return flag;
    }

    public static boolean isProductCode(String barCode) {
        boolean flag = false;
        if (Pattern.compile("\\d{6}").matcher(barCode).matches())
            flag = true;
        return flag;
    }


    public static boolean isDate(String saleTime){
        boolean flag=false;
        String regex="\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|(1[0-9])|2[0-9]|3[0-1])";
        if(Pattern.compile(regex).matcher(saleTime).matches())
            flag=true;
        return flag;
    }

    public String getDate(String a) throws Exception {
        Date date = new Date();
        if (a == "cur") {
            DateFormat format2 = new SimpleDateFormat("yyyyMMdd");
            String time2 = format2.format(date);
            return time2;
        } else if (a == "time") {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            return time;
        } else
            return null;
    }
}
