import uk.co.therhys.Request.Net.Net;
import uk.co.therhys.Request.Net.Result;

import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        Net net = Net.getInstance();

        Result ret = net.get("http://therhys.co.uk/insecure/proxy.php?q=https://inv.riverside.rocks/api/v1/channels/UC1D3yD4wlPMico0dss264XA", new HashMap());

        System.out.println(ret.hadError);
    }
}
