package org.example;

import org.example.L4Packets.*;

public class PacketFactory {

    public static L3Packet parseL3Packet(L2Packet l2Packet) {
        switch (l2Packet.ethType) {
            case "IPV4" -> {
                return new IPv4Packet(l2Packet.payload);
            }
            case "IPV6" -> {
                return new IPv6Packet(l2Packet.payload);
            }
            case "ARP" -> {
                return new ARPPacket(l2Packet.payload);
            }
            //case "LLDP" -> {return new LLDPacket(l2Packet.payload);}
            default -> {
                return null;
            }
        }
    }

    public static L4Packet parseL4Packet(L3Packet l3Packet){
        if(l3Packet == null)return null;

        if(l3Packet.getProtocol() == null)return null;

        switch (l3Packet.getProtocol()){
            case "TCP" ->{
                return new TCPPacket(l3Packet.getPayload(), "TCP");
            }
            case "UDP" -> {
                return new UDPPacket(l3Packet.getPayload(), "UDP");
            }
            case "ICMPv6" -> {
                return new ICMPv6(l3Packet.getPayload(), "ICMPv6");
            }
            case "ICMPv4" -> {
                return new ICMPv4(l3Packet.getPayload(), "ICMPv4");
            }
            case "IGMP" -> {
                return new IGMP(l3Packet.getPayload(), "IGMP");
            }
            default ->{
                return null;
            }
        }
    }
}
