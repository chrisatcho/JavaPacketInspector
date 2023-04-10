package org.example;

public class ARPPacket extends L3Packet{
    String hardwareType, protocolType, hardwareSize, protocolSize, opcode, senderMac, senderIP, targetMac, targetIP, payload;

    public ARPPacket(String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.hardwareType   = s.substring(0, 16);
        this.protocolType   = s.substring(16, 32);
        this.hardwareSize   = s.substring(32, 40);
        this.protocolSize   = s.substring(40, 48);
        this.opcode         = s.substring(48, 56);
        this.senderMac      = s.substring(56, 72);
        this.senderIP       = s.substring(72, 104);
        this.targetMac      = s.substring(104, 120);
        this.targetIP       = s.substring(120, 152);
        this.payload        = s.substring(152);
    }

    public void printAll(){
        System.out.println("Address Resolution Protocol, Src: " + this.senderIP + ", Dst: " + this.targetIP);
        if (this.targetIP.equals(this.senderIP)) {
            System.out.println("ARP Announcement for " + this.targetIP);
        } else {
            System.out.println("Who has " + this.targetIP + "? Tell " + this.senderIP);
        }
    }
    public void printSrcIP() {
        System.out.println(this.senderIP);
    }
    public void printDestIP() {
        System.out.println(this.targetIP);
    }
    public String getProtocol() {
        return this.opcode;
    }
    public void printProtocol() {
        System.out.println(this.opcode);
    }
    public void printPayload() {
        System.out.println(this.payload);
    }
}
