package uk.co.therhys.Request.Net;

import cz.adamh.utils.NativeUtils;

import java.util.HashMap;
import java.util.Map;

public class MacNet extends Net {
    static {
        try {
            NativeUtils.loadLibraryFromJar("/libnet.dylib");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result post_auth(String urlStr, String data, HashMap headers, String username, String password) {
        try {
            byte[] authBytes = (username + ":" + password).getBytes("UTF-8");
            String authStr = "Basic " + new Base64().encode(authBytes);
            headers.put("Authorization", authStr);
        }catch (Exception e){
            System.err.println("Could not convert base64 sequence correctly!");
            e.printStackTrace();
            return null;
        }

        return post(urlStr, data, headers);
    }

    public Result post(String urlStr, String data, HashMap headers) {
        String[][] headersArry = new String[headers.size()][2];

        for(int i=0 ; i<headersArry.length ; i++){
            Map.Entry entry = (Map.Entry) headers.entrySet().toArray()[i];
            headersArry[i][0] = (String) entry.getKey();
            headersArry[i][1] = (String) entry.getValue();
        }

        Result out = new Result();

        byte[] res = post_auth(urlStr, data, headersArry);
        if(res.length == 0){
            out.hadError = true;
            out.error = null;
        }else{
            out.data = res;
            out.length = res.length;
        }

        return out;
    }

    public Result get(String urlStr, HashMap headers) {
        String[][] headersArry = new String[headers.size()][2];

        for(int i=0 ; i<headersArry.length ; i++){
            Map.Entry entry = (Map.Entry) headers.entrySet().toArray()[i];
            headersArry[i][0] = (String) entry.getKey();
            headersArry[i][1] = (String) entry.getValue();
        }

        Result out = new Result();

        byte[] res = get(urlStr, headersArry);
        if(res.length == 0){
            out.hadError = true;
            out.error = null;
        }else{
            out.data = res;
            out.length = res.length;
        }

        return out;
    }

    private native byte[] get(String url, String[][] headers);
    private native byte[] post_auth(String url, String data, String[][] headers);
}
