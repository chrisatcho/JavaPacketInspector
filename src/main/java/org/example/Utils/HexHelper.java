package org.example.Utils;

public class HexHelper {
    public static long hexToLong(String hex){
        try{
            return Long.parseLong(hex, 16);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int hexToInt(String hex){
        try{
            return Integer.parseInt(hex, 16);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static String hexToBinaryString(String hex){
        return Integer.toBinaryString(hexToInt(hex));
    }
}
