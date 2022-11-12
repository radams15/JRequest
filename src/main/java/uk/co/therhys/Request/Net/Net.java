package uk.co.therhys.Request.Net;

import java.io.File;
import java.util.HashMap;

public class Net {

    private static Net instance;

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
        throw new RuntimeException("Net unimplemented!");
    }

    public Result get(String urlStr, HashMap headers){
        throw new RuntimeException("Net unimplemented!");
    }

    public boolean download(String urlStr, HashMap headers, String outPath){
        Result data = get(urlStr, headers);

        if(data.hadError){
            data.error.printStackTrace();
            return true;
        }

        FileUtils.writeFile(new File(outPath), data.data);

        return false;
    }

    public static Net getInstance(){
        if(instance == null){
            instance = newNet();
        }

        return instance;
    }

    private static Net newNet(){
        if(OS.getOS() == OS.OSX && ! OS.versionAbove("10.6")){ // If OSX < snow leopard use the library (ppc)
            System.out.println("Using NSHttpClient");
            return new MacNet();
        }else{
            System.out.println("Using HTTPUrlConnection");
            return new UrlNet();
        }
    }
}
