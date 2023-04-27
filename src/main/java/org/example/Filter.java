package org.example;

import org.example.L4Packets.*;
public class Filter {
    private Subnet SourceSubnet;
    private Subnet DestSubnet;
    private String protocol;
    private int SourcePort;
    private int DestPort;

    public Filter(String srcIP, String destIP, String srcPort, String destPort, String protocol) {
        createFilter(srcIP, destIP, Integer.parseInt(srcPort), Integer.parseInt(destPort), protocol);
    }

    public void createFilter(String srcIP, String destIP, int srcPort, int destPort, String protocol) {
        this.SourceSubnet = new Subnet(srcIP);
        this.SourcePort = srcPort;
        this.DestPort = destPort;
        this.DestSubnet = new Subnet(destIP);
        this.protocol = protocol;
    }
    public boolean check(L3Packet l3packet) {
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
        if (!(l4packet.getSourcePort() == this.SourcePort)) {
            return false;
        }
        if (!(l4packet.getDestPort() == this.DestPort)) {
            return false;
        }
        return true;
    }
}