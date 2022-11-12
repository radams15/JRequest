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

            for(int i=0 ; i<headers.entrySet().size() ; i++){
                Map.Entry entry = (Map.Entry) headers.entrySet().toArray()[i];

                http.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }

            byte[] toSend = data.getBytes();

            //http.setFixedLengthStreamingMode(toSend.length);

            if(!headers.containsKey("Content-Type")){
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }

            http.connect();

            OutputStream os = null;
            try {
                os = http.getOutputStream();
                os.write(toSend);
            }finally {
                if(os != null) {
                    os.close();
                }
            }

            int length = http.getContentLength();
            if(length == -1){
                length = 1000*1000; // = 1MB;
            }

            InputStream is =  new BufferedInputStream(http.getInputStream());

            int actualLength = 0;

            ByteBuffer buf = ByteBuffer.allocate(length);
            byte[] line = new byte[chunkSize];
            while(true){
                int len = is.read(line, 0, chunkSize);
                if(len != -1) {
                    actualLength += len;
                    for(int i=0 ; i<len ; i++){
                        buf.put(line[i]);
                    }
                }else{
                    break;
                }
            }

            res.data = buf.compact().array();

            res.length = actualLength;

        }catch (Exception e){
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

            if(!headers.containsKey("Content-Type")){
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }

            for(int i=0 ; i<headers.entrySet().size() ; i++){
                Map.Entry entry = (Map.Entry) headers.entrySet().toArray()[i];

                http.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }

            http.connect();

            int length = http.getContentLength();
            if(length == -1){
                length = 1000*1000; // = 1MB;
            }

            InputStream is =  new BufferedInputStream(http.getInputStream());

            int actualLength = 0;
            ByteBuffer buf = ByteBuffer.allocate(length);
            byte[] line = new byte[chunkSize];
            while(true){
                int len = is.read(line, 0, chunkSize);
                if(len != -1) {
                    actualLength += len;
                    for(int i=0 ; i<len ; i++){
                        buf.put(line[i]);
                    }
                }else{
                    break;
                }
            }

            res.data = buf.compact().array();

            res.length = actualLength;

        }catch (Exception e){
            res.hadError = true;
            res.error = e;
            return res;
        }

        return res;
    }
}
