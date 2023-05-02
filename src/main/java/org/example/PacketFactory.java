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
            case "LLDP" -> {
                return new LLDPacket(l2Packet.payload);
            }
            default -> {
                return null;
            }
        }
    }

    public static L4Packet parseL4Packet(L3Packet l3Packet){
        if(l3Packet.getProtocol() == null)return null;
        switch (l3Packet.getProtocol()){
            case "TCP" ->{
                return new TCPPacket(l3Packet.getPayload(), "TCP");
            }
            case "UDP" -> {
                return new UDPPacket(l3Packet.getPayload(), "UDP");
            }
            default ->{
                return null;
            }
        }
    }
}
