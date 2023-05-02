package org.example;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.net.InetAddress;

public class ARPTable {
    HashMap<String, String> arpTable;
    public ARPTable(){
        this.arpTable = new HashMap<String, String>();
    }

    public void add(String IP, String id){
        this.arpTable.put(IP, id);
    }

    boolean contains(String IP){
        return this.arpTable.containsKey(IP);
    }

    public String getDevID(String IP){
        //IPv6 packets cannot be searched for hostname so just returns the input
        if(IP.length() > 15) {
            return IP;
        }

        if(contains(IP)){
            return this.arpTable.get(IP);
        } else {
            String hostName = getID(IP);
            if(!hostName.equals(IP))this.arpTable.put(IP, hostName);
            return hostName;
        }
    }

    public static String getID(String IP){
        try{
            InetAddress ipAddress;
            if(IP.contains(":")){
                ipAddress = InetAddress.getByName(IP);
            }
            else{
                ipAddress = InetAddress.getByAddress(getIP(IP));
            }
            String hostname = ipAddress.getCanonicalHostName();
            return hostname;
        }
        catch(UnknownHostException e){
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] getIP(String ip){
        String[] nums = ip.split("\\.");
        return new byte[]{(byte) Integer.parseInt(nums[0]), (byte) Integer.parseInt(nums[1]),(byte) Integer.parseInt(nums[2]),(byte) Integer.parseInt(nums[3])};
    }
}
