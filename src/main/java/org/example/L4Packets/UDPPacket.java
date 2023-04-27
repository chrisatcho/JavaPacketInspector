package org.example.L4Packets;

import org.example.L4Packet;
import org.example.Utils.HexHelper;
public class UDPPacket extends L4Packet {
    String protocol;
    int sourcePort, destPort;
    int length;
    String checksum;
    String payload;
    public UDPPacket(String payload, String protocol){
        this.protocol = protocol;

        this.sourcePort = HexHelper.hexToInt(payload.substring(0,4));

        this.destPort = HexHelper.hexToInt(payload.substring(4,8));

        this.length = HexHelper.hexToInt(payload.substring(8,12));

        this.checksum = payload.substring(12,16);
        this.payload = payload.substring(16);
    }
    @Override
    public void printPayload() {
        System.out.println(this.payload);
    }

    @Override
    public void printAll() {
        System.out.println("UDP " + this.sourcePort + " > " + this.destPort + " Length: " + this.length);
    }
    public int getSourcePort(){return this.sourcePort;}
    public int getDestPort(){return this.destPort;}
}
