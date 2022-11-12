package uk.co.therhys.Request.Net;

public class Base64 {
    public static String encode(byte[] input) throws Exception {
        try{
            //return Class.forName("sun.misc.BASE64Encoder").getMethod("encode").invoke(new Object[]{input}).toString();
            return new sun.misc.BASE64Encoder().encode(input);
        }catch (ClassNotFoundException e) {
            return new String(java.util.Base64.getEncoder().encode(input));
        }
    }
}
