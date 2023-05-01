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


    public void printAll(String srcIPHostname, String destIPHostname, boolean verbose){
        if(verbose) System.out.println(getString(srcIPHostname,destIPHostname));
        else System.out.print(getShortString(srcIPHostname, destIPHostname));
    }

    @Override
    public String getShortString(String srcIPHostname, String destIPHostname) {
        String sendHostip;
        sendHostip = (this.senderIP.equals((srcIPHostname)) ? "" : "("+srcIPHostname+")");

        String destHostip;
        destHostip = (this.targetIP.equals((destIPHostname)) ? "" : "("+ destIPHostname + ")");

        String output = "";
        if (this.targetIP.equals(this.senderIP)) {
            output += " ARP Announcement for " + this.targetIP + destIPHostname + " ";
        } else {
            output += " ARP Who has " + this.targetIP + destIPHostname+ "? Tell " + this.senderIP + srcIPHostname;
        }
        return output;
    }

    public String getString(String srcIPHostname, String destIPHostname){
        String sendHostip;
        sendHostip = (this.senderIP.equals((srcIPHostname)) ? "" : "("+srcIPHostname+")");

        String destHostip;
        destHostip = (this.targetIP.equals((destIPHostname)) ? "" : "("+ destIPHostname + ")");

        String output = "Address Resolution Protocol, Src: " + this.senderIP + sendHostip + ", Dst: " + this.targetIP + destHostip+ "\n";
        if (this.targetIP.equals(this.senderIP)) {
            output += "ARP Announcement for " + this.targetIP;
        } else {
            output += "Who has " + this.targetIP + "? Tell " + this.senderIP;
        }
        return output;
    }
    public void printSrcIP() {
        System.out.println(this.senderIP);
    }
    public void printDestIP() {
        System.out.println(this.targetIP);
    }
    public void printProtocol() {
        System.out.println(this.opcode);
    }
    public void printPayload() {
        System.out.println(this.payload);
    }
    public String getSrcIP() { return this.senderIP; }
    public String getDestIP() { return this.targetIP; }
    public String getProtocol() { return this.opcode; }
    public String getPayload() { return this.payload; }
}
