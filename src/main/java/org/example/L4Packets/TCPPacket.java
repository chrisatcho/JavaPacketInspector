package org.example.L4Packets;

import org.example.*;
import org.example.Utils.HexHelper;

public class TCPPacket extends L4Packet {
    String protocol;
    int sourcePort, destPort;
    long seqNum, ackNum;
    long dataOffset;
    boolean CWR, ECE, URG, ACK, PSH, RST, SYN, FIN;
    long windowSize;
    String checksum;
    long urgPointer;

    String payload;
    public TCPPacket(String payload, String protocol){
        this.protocol = protocol;
        this.sourcePort = HexHelper.hexToInt(payload.substring(0,4));
        this.destPort = HexHelper.hexToInt(payload.substring(4,8));

        this.seqNum = HexHelper.hexToLong(payload.substring(8,16));
        this.ackNum = HexHelper.hexToLong(payload.substring(16,24));

        this.dataOffset = HexHelper.hexToLong(payload.substring(24,25));

        int flags = HexHelper.hexToInt(payload.substring(26,28));
        this.CWR = (flags & 0x80) != 0;
        this.ECE = (flags & 0x40) != 0;
        this.URG = (flags & 0x20) != 0;
        this.ACK = (flags & 0x10) != 0;
        this.PSH = (flags & 0x08) != 0;
        this.RST = (flags & 0x04) != 0;
        this.SYN = (flags & 0x02) != 0;
        this.FIN = (flags & 0x01) != 0;

        this.windowSize = HexHelper.hexToLong(payload.substring(28,32));

        this.checksum = payload.substring(32,36);
        this.urgPointer = HexHelper.hexToLong(payload.substring(36,40));

        if(this.windowSize > 5){
            this.payload = payload.substring(40);
        }
    }
    @Override
    public void printAll() {
        String print = this.protocol + " " + this.sourcePort + " > " + this.destPort;
        System.out.println(print);
    }

    @Override
    public void printPayload(){
        System.out.println(this.payload);
    }

    public int getSourcePort(){return this.sourcePort;}
    public int getDestPort(){return this.destPort;}

    public String getString(){return this.protocol + " " + this.sourcePort + " > " + this.destPort + " Length: " +this.windowSize;}
}
