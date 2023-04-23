package org.example;

public class Filter {
    private Subnet SourceSubnet;
    private Subnet DestSubnet;
    private String protocol;

    public Filter(String srcIP, String destIP, String srcPort, String destPort, String protocol) {
        createFilter(srcIP, destIP, srcPort, destPort, protocol);
    }

    public void createFilter(String srcIP, String destIP, String srcPort, String destPort, String protocol) {
        this.SourceSubnet = new Subnet(srcIP);
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
}
