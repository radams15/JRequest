package uk.co.therhys.Request.Net;

public class Result {
    public byte[] data;
    public int length;
    public boolean hadError = false;
    public Exception error;

    public String toString(){
        return new String(data);
    }
}