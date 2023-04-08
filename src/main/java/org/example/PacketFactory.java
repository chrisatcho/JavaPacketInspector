package org.example;

public class PacketFactory {
    public static Packet getPacket(byte[] bs) {
        L2Packet l2Packet = new L2Packet(bs);
        L3Packet l3Packet = new L3Packet(L2Packet.getPayload());
    }
}
