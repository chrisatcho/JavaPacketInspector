package org.example;

public class LLDPacket extends L3Packet {
    String chassisID, portID, ttl, payload;

    public LLDPacket(String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.chassisID  = s.substring(0, 16);
        this.portID     = s.substring(16, 32);
        this.ttl        = s.substring(32, 48);
        this.payload    = s.substring(48);
    }

    public void printAll() {
        System.out.println("Link Layer Discovery Protocol, Src: " + this.chassisID + ", Dst: " + this.portID);
    }
    public void printSrcIP() {
        System.out.println(this.chassisID);
    }
    public void printDestIP() {
        System.out.println(this.portID);
    }
    public void printProtocol() {
        System.out.println(this.ttl);
    }
    public void printPayload() {
        System.out.println(this.payload);
    }
    public String getSrcIP() { return this.chassisID; }
    public String getDestIP() { return this.portID; }
    public String getProtocol() {
        return this.ttl;
    }
    public String getPayload() { return this.payload; }
}
