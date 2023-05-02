package org.example;

import org.example.L4Packets.*;
public class Filter {
    private Subnet SourceSubnet;
    private Subnet DestSubnet;
    private String protocol;
    private int SourcePort;
    private int DestPort;
    private boolean verbose;

    public Filter(String srcIP, String destIP, String srcPort, String destPort, String protocol, String verbose) {
        createFilter(srcIP, destIP, Integer.parseInt(srcPort), Integer.parseInt(destPort), protocol, verbose);
    }

    public void createFilter(String srcIP, String destIP, int srcPort, int destPort, String protocol, String verbose) {
        this.SourceSubnet = new Subnet(srcIP);
        this.SourcePort = srcPort;
        this.DestPort = destPort;
        this.DestSubnet = new Subnet(destIP);
        this.protocol = protocol;
        this.verbose = verbose.equals("2");
    }
    public boolean check(L3Packet l3packet) {
        if(l3packet == null) return false;

        if(l3packet instanceof IPv6Packet){

            if(!l3packet.getProtocol().equals(this.protocol) && !this.protocol.equals(""))return false;
            return true;
        }
        if(l3packet instanceof ARPPacket){
            return true;
        }

        if (!l3packet.getProtocol().equals(this.protocol) && !this.protocol.equals("")) {
            return false;
        }
        if (!this.SourceSubnet.IPinRange(l3packet.getSrcIP())) {
            return false;
        }
        if (!this.DestSubnet.IPinRange(l3packet.getDestIP())) {
            return false;
        }
        return true;
    }
    public boolean check(L4Packet l4packet) {
        if(l4packet == null)return true;
        if (!(l4packet.getSourcePort() == this.SourcePort) && this.SourcePort != -1) {
            return false;
        }
        if (!(l4packet.getDestPort() == this.DestPort) && this.DestPort != -1) {
            return false;
        }
        return true;
    }

    public boolean isVerbose(){
        return this.verbose;
    }
}
