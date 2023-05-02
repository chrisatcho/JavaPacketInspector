package org.example;

public class ARPPacket extends L3Packet{
    String hardwareType, protocolType, hardwareSize, protocolSize, opcode, senderMac, senderIP, targetMac, targetIP, payload;

    public ARPPacket(String s) {
        parseVariables(s);
    }

    public void parseVariables(String s) {
        this.hardwareType   = s.substring(0, 4);
        this.protocolType   = s.substring(4, 8);
        this.hardwareSize   = s.substring(8, 10);
        this.protocolSize   = s.substring(10, 12);
        this.opcode         = s.substring(12, 16);
        this.senderMac      = s.substring(16, 28);
        this.senderIP       = this.getIPfrom8Hex(s.substring(28, 36));
        this.targetMac      = s.substring(36, 48);
        this.targetIP       = this.getIPfrom8Hex(s.substring(48,56));
        this.payload        = s.substring(56);
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
            output += "ARP Announcement for " + this.targetIP + destHostip;
        } else {
            output += "Who has " + this.targetIP + destHostip+ "? Tell " + this.senderIP + sendHostip;
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

    public String getIPfrom8Hex(String s){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < 4; ++i){
            output.append(Integer.parseInt(s.substring(i*2, i*2+2), 16));
            if(i < 3)output.append(".");
        }
        return output.toString();
    }
}
