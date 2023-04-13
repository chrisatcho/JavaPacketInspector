package org.example.Utils;

public class HexHelper {
    public static int hexToInt(String hex){
        return Integer.parseInt(hex, 16);
    }

    public static String hexToBinaryString(String hex){
        return Integer.toBinaryString(hexToInt(hex));
    }
}
