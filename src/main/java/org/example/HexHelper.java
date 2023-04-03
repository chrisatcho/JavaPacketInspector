package org.example;

public class HexHelper {
    static public int getIntFromSingleHex(char hex) {
        return Integer.parseInt(String.valueOf(hex), 16);
    }

    static public int getIntFromHexString(String twoHex){
        return Integer.parseInt(String.valueOf(twoHex),16);
    }

    String getFlags(String hex) {
        return hexToBin(hex).substring(0, 3);
    }


    static String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    static public String getIPFromHex(String hexIP) {
        String ip = "";
        for (int i = 0; i < 4; ++i) {
            ip += getIntFromHexString(hexIP.substring(i * 2, i * 2 + 2));
            ip += ".";
        }
        return ip.substring(0, ip.length() - 1);
    }
}
