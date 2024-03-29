package uk.co.therhys.Request.Net;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class UrlNet extends Net {

    private final static int chunkSize = 1024;
    public Result post(String urlStr, String data, HashMap headers){
        Result res = new Result();

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");

            for(int i=0 ; i<headers.entrySet().size() ; i++){
                Map.Entry entry = (Map.Entry) headers.entrySet().toArray()[i];

                http.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }

            OutputStream os = http.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(data);
            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream

            //http.setFixedLengthStreamingMode(toSend.length);

            if(!headers.containsKey("Content-Type")){
                headers.put("Content-Type", "text/html; charset=UTF-8");
            }

            http.connect();

            InputStream is =  new BufferedInputStream(http.getInputStream());

            int actualLength = 0;
            StringBuffer buf = new StringBuffer();

            int byteRes = is.read();
            while(byteRes != -1){
                actualLength += byteRes;
                buf.append((char) byteRes);
                byteRes = is.read();
            }

            res.data = buf.toString().getBytes();

            res.length = actualLength;

        }catch (Exception e){
            System.err.println(e);
            res.hadError = true;
            res.error = e;
            return res;
        }

        return res;
    }

    public Result get(String urlStr, HashMap headers){
        Result res = new Result();

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");

            if(!headers.containsKey("Content-Type")){
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }

            for(int i=0 ; i<headers.entrySet().size() ; i++){
                Map.Entry entry = (Map.Entry) headers.entrySet().toArray()[i];

                http.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }

            http.connect();

            InputStream is =  new BufferedInputStream(http.getInputStream());

            int actualLength = 0;
            StringBuffer buf = new StringBuffer();

            int byteRes = is.read();
            while(byteRes != -1){
                actualLength += byteRes;
                buf.append((char) byteRes);
                byteRes = is.read();
            }

            System.out.println(buf);

            res.data = buf.toString().getBytes();

            res.length = actualLength;

        }catch (Exception e){
            res.hadError = true;
            res.error = e;
            return res;
        }

        return res;
    }
}
