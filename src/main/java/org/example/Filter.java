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


    public boolean check(L4Packet l4, L3Packet l3){
        if(!protocolCheck(l4,l3) || !portCheck(l4) || !subnetCheck(l3)) {
            return false;
        }


        return true;
    }

    private boolean portCheck(L4Packet l4){
        if(this.SourcePort == -1 && this.DestPort == -1)return true;
        if(l4 == null)return false;

        if(this.SourcePort != -1){
            if(l4.getSourcePort() == this.SourcePort)return true;
        }
        else {
            if(l4.getDestPort() == this.DestPort)return true;
        }
        return false;
    }
    private boolean protocolCheck(L4Packet l4, L3Packet l3){
        if(l3 != null){
            if(this.protocol.equals(""))return true;

            if(this.protocol.toLowerCase().equals("arp"))
                if(l3 instanceof ARPPacket)return true;
            if(this.protocol.toLowerCase().equals("ipv6") || this.protocol.toLowerCase().equals("ip6"))
                    if(l3 instanceof IPv6Packet)return true;
            if(this.protocol.toLowerCase().equals("ipv4") || this.protocol.toLowerCase().equals("ip4"))
                    if(l3 instanceof IPv4Packet)return true;

        } else return false;

        if(l4 == null)return false;

        if(this.protocol.toLowerCase().equals("udp") && l4 instanceof UDPPacket)return true;
        if(this.protocol.toLowerCase().equals("tcp") && l4 instanceof  TCPPacket) return true;
        if(this.protocol.toLowerCase().equals("igmp") && l4 instanceof IGMP) return true;
        if(this.protocol.toLowerCase().equals("icmpv4") && l4 instanceof ICMPv4) return true;
        if(this.protocol.toLowerCase().equals("icmpv6") && l4 instanceof ICMPv6) return true;

        return false;
    }
    private boolean subnetCheck(L3Packet l3){
        if(l3 == null)return false;

        if(this.SourceSubnet.getStringVal().equals("0.0.0.0/32") && this.DestSubnet.getStringVal().equals("0.0.0.0/32"))return true;

        if(!(l3 instanceof ARPPacket || l3 instanceof IPv4Packet))return false;

        if (!this.SourceSubnet.IPinRange(l3.getSrcIP())) {
            return false;
        }
        if (!this.DestSubnet.IPinRange(l3.getDestIP())) {
            return false;
        }
        return true;
    }
    public boolean isVerbose(){
        return this.verbose;
    }
}
