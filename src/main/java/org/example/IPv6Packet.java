package org.example;

public class IPv6Packet extends L3Packet {
    String version, trafficClass, flowLabel, payloadLength, nextHeader, hopLimit, srcIP, destIP, payload;

    public IPv6Packet (String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {

        this.version        = s.substring(0, 1);
        this.trafficClass   = s.substring(1, 3);
        this.flowLabel      = s.substring(3, 8);
        this.payloadLength  = s.substring(8, 12);
        this.nextHeader     = s.substring(12, 14);
        this.hopLimit       = s.substring(14, 16);
        this.srcIP          = s.substring(16, 48);
        this.destIP         = s.substring(48, 80);
        this.payload        = s.substring(80);
    }

    public void printAll(String srcIPHostname, String destIPHostname, boolean verbose) {
        if(verbose) System.out.println(this.getString(srcIPHostname, destIPHostname));
        else System.out.print(getShortString(srcIPHostname, destIPHostname));
    }
    public String getString(String srcIPHostname, String destIPHostname){return "Internet Protocol Version 6, Src: " + this.getFormattedIP(this.srcIP) + ", Dst: " + this.getFormattedIP(this.destIP) + " " + this.getProtocolFromNumbers(this.nextHeader);}
    public String getShortString(String srcIPHostname, String destIPHostname){return " IPv6 " + this.getFormattedIP(this.srcIP) + "-->" + this.getFormattedIP(this.destIP) + " " + this.getProtocolFromNumbers(this.nextHeader);}

    public void printSrcIP() {
        System.out.println(this.srcIP);
    }
    public void printDestIP() {
        System.out.println(this.destIP);
    }
    public void printNextHeader() {
        System.out.println(this.nextHeader);
    }
    public void printProtocol(){System.out.println(this.getProtocolFromNumbers(this.nextHeader));}
    public void printPayload() {
        System.out.println(this.payload);
    }
    public String getSrcIP() { return this.srcIP; }
    public String getDestIP() { return this.destIP; }
    public String getProtocol() {
        return this.getProtocolFromNumbers(this.nextHeader);
    }
    public String getPayload() { return this.payload; }
    public String getFormattedIP(String s){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < 8; ++i){
            String substring = s.substring(i * 4, i * 4 + 4);

            if(substring.equals("0000")) output.append("0");
            else {
                output.append(substring);
            }
            if(i < 7)output.append(":");
        }
        return output.toString();
    }

    public String getProtocolFromNumbers(String protocolNum){
        return switch(protocolNum){
            case "06" -> "TCP";
            case "11" -> "UDP";

            default -> "";
        };
    }

}
