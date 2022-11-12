package uk.co.therhys.Request.Net;

public class Base64 {
    public static String encode(byte[] input) throws Exception {
        try{
            Class.forName("sun.misc.BASE64Encoder");
            return new sun.misc.BASE64Encoder().encode(input);
        }catch (ClassNotFoundException e) {
            return new String(java.util.Base64.getEncoder().encode(input));
        }
    }
}
