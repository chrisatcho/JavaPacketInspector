package org.example;

public class IPv4Packet extends L3Packet {
    String version, IHL, DSCP, ECN, totalLength, identification, flags, fragmentOffset, TTL, protocol, headerChecksum, srcIP, destIP, options, padding, payload;

    public IPv4Packet (String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.version         = s.substring(0, 4);
        this.IHL             = s.substring(4, 8);
        this.DSCP            = s.substring(8, 14);
        this.ECN             = s.substring(14, 16);
        this.totalLength     = s.substring(16, 32);
        this.identification  = s.substring(32, 48);
        this.flags           = s.substring(48, 51);
        this.fragmentOffset  = s.substring(51, 64);
        this.TTL             = s.substring(64, 72);
        this.protocol        = s.substring(72, 80);
        this.headerChecksum  = s.substring(80, 96);
        this.srcIP           = s.substring(96, 128);
        this.destIP          = s.substring(128, 160);
        this.options         = s.substring(160, Integer.parseInt(IHL, 2) * 8);
        this.payload         = s.substring(Integer.parseInt(IHL, 2) * 8);
    }

    public void printAll() {
        System.out.println("Internet Protocol Version 4, Src: " + this.srcIP + ", Dst: " + this.destIP);
    }
    public void printSrcIP() {
        System.out.println(this.srcIP);
    }
    public void printDestIP() {
        System.out.println(this.destIP);
    }
    public String getProtocol() {
        return this.protocol;
    }
    public void printProtocol() {
        System.out.println(this.protocol);
    }
    public void printPayload() {
        System.out.println(this.payload);
    }
}
