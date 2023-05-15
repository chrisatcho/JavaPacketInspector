package org.example;

public class LLDPacket extends L3Packet {
    String chassisID, portID, ttl, payload;

    public LLDPacket(String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.chassisID  = s.substring(0, 8);
        this.portID     = s.substring(8, 16);
        this.ttl        = s.substring(16, 24);
        this.payload    = s.substring(24);
    }

    public void printAll(String srcIPHostname, String destIPHostname, boolean verbose) {
        if(verbose) System.out.println("Link Layer Discovery Protocol, Src: " + this.chassisID + ", Dst: " + this.portID);
        else System.out.print(getShortString(srcIPHostname, destIPHostname));
    }
    public String getString(String srcIPHostname, String destIPHostname){return "Link Layer Discovery Protocol, Src: " + this.chassisID + ", Dst: " + this.portID;}
    public String getShortString(String srcIPHostname, String destIPHostname){return " LLDP, " + this.chassisID + "-->" + this.portID;}
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
