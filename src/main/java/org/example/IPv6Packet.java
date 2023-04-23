package org.example;

public class IPv6Packet extends L3Packet {
    String version, trafficClass, flowLabel, payloadLength, nextHeader, hopLimit, srcIP, destIP, payload;

    public IPv6Packet (String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.version        = s.substring(0, 4);
        this.trafficClass   = s.substring(4, 16);
        this.flowLabel      = s.substring(16, 32);
        this.payloadLength  = s.substring(32, 48);
        this.nextHeader     = s.substring(48, 56);
        this.hopLimit       = s.substring(56, 64);
        this.srcIP          = s.substring(64, 128);
        this.destIP         = s.substring(128, 192);
        this.payload        = s.substring(192);
    }

    public void printAll() {
        System.out.println("Internet Protocol Version 6, Src: " + this.srcIP + ", Dst: " + this.destIP);
    }
    public void printSrcIP() {
        System.out.println(this.srcIP);
    }
    public void printDestIP() {
        System.out.println(this.destIP);
    }
    public void printProtocol() {
        System.out.println(this.nextHeader);
    }
    public void printPayload() {
        System.out.println(this.payload);
    }
    public String getSrcIP() { return this.srcIP; }
    public String getDestIP() { return this.destIP; }
    public String getProtocol() {
        return this.nextHeader;
    }
    public String getPayload() { return this.payload; }
}
