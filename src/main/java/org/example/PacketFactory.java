package org.example;

public class PacketFactory {
    public static L2Packet parseL2Packet(byte[] bs) {
        return new L2Packet(bs);
    }
    public static L3Packet parseL3Packet(L2Packet l2Packet) {
        switch (l2Packet.ethType) {
            case IPV4 -> {
                return new IPv4Packet(l2Packet.payload);
            }
            case IPV6 -> {
                return new IPv6Packet(l2Packet.payload);
            }
            case ARP -> {
                return new ARPPacket(l2Packet.payload);
            }
            case LLDP -> {
                return new LLDPacket(l2Packet.payload);
            }
            default -> {
                return null;
            }
        }
    }
}
