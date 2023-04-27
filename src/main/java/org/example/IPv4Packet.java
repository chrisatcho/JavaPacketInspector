package org.example;

public class IPv4Packet extends L3Packet {
    String version, IHL, DSCP_ECN, totalLength, identification, flags, fragmentOffset, TTL, protocol, headerChecksum, srcIP, destIP, options, payload;

    public IPv4Packet (String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.version         = s.substring(0, 1);
        this.IHL             = s.substring(1, 2);
        this.DSCP_ECN        = s.substring(2, 4);
        this.totalLength     = s.substring(4, 8);
        this.identification  = s.substring(8, 12);
        this.flags           = s.substring(12, 13);
        this.fragmentOffset  = s.substring(13, 16);
        this.TTL             = s.substring(16, 18);
        this.protocol        = matchProtocol(s.substring(18, 20));
        this.headerChecksum  = s.substring(20, 24);
        this.srcIP           = hexToIP(s.substring(24, 32));
        this.destIP          = hexToIP(s.substring(32, 40));
        if (Integer.parseInt(IHL, 16) * 2 > 40) {
            this.options     = s.substring(40, Integer.parseInt(IHL, 16) * 2);
            this.payload     = s.substring(Integer.parseInt(IHL, 16) * 2);
        } else {
            this.options     = "";
            this.payload     = s.substring(40);
        }
    }

    private static String hexToIP(String s) {
        return Integer.parseInt(s.substring(0, 2), 16) + "." +
                Integer.parseInt(s.substring(2, 4), 16) + "." +
                Integer.parseInt(s.substring(4, 6), 16) + "." +
                Integer.parseInt(s.substring(6, 8), 16);
    }
    public void printAll() {
        System.out.println("Internet Protocol Version 4, Src: " + this.srcIP + ", Dst: " + this.destIP + ", Protocol: " + this.protocol);
    }
    public String getString(){return "Internet Protocol Version 4, Src: " + this.srcIP + ", Dst: " + this.destIP + ", Protocol: " + this.protocol;}
    public void printSrcIP() {
        System.out.println(this.srcIP);
    }
    public void printDestIP() {
        System.out.println(this.destIP);
    }
    public void printProtocol() {
        System.out.println(this.protocol);
    }
    public void printPayload() {
        System.out.println(this.payload);
    }
    public String getSrcIP() { return this.srcIP; }
    public String getDestIP() { return this.destIP; }
    public String getProtocol() {
        return this.protocol;
    }
    public String getPayload() { return this.payload; }
}
