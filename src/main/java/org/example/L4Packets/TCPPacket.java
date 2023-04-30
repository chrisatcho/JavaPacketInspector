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
    int flags;
    String payload;
    public TCPPacket(String payload, String protocol){
        this.protocol = protocol;
        this.sourcePort = HexHelper.hexToInt(payload.substring(0,4));
        this.destPort = HexHelper.hexToInt(payload.substring(4,8));

        this.seqNum = HexHelper.hexToLong(payload.substring(8,16));
        this.ackNum = HexHelper.hexToLong(payload.substring(16,24));

        this.dataOffset = HexHelper.hexToLong(payload.substring(24,25));

        this.flags = HexHelper.hexToInt(payload.substring(26,28));
        this.CWR = (this.flags & 0x80) != 0;
        this.ECE = (this.flags & 0x40) != 0;
        this.URG = (this.flags & 0x20) != 0;
        this.ACK = (this.flags & 0x10) != 0;
        this.PSH = (this.flags & 0x08) != 0;
        this.RST = (this.flags & 0x04) != 0;
        this.SYN = (this.flags & 0x02) != 0;
        this.FIN = (this.flags & 0x01) != 0;

        this.windowSize = HexHelper.hexToLong(payload.substring(28,32));

        this.checksum = payload.substring(32,36);
        this.urgPointer = HexHelper.hexToLong(payload.substring(36,40));

        if(this.windowSize > 5){
            this.payload = payload.substring(40);
        }
    }
    @Override
    public void printAll() {
        String print = this.protocol + " " + this.sourcePort + " > " + this.destPort + " "+  getFlagsString() +" Length: " +this.windowSize;;
        System.out.println(print);
    }

    @Override
    public void printPayload(){
        System.out.println(this.payload);
    }

    public int getSourcePort(){return this.sourcePort;}
    public int getDestPort(){return this.destPort;}

    public String getString(){return this.protocol + " " + this.sourcePort + " > " + this.destPort + " "+  getFlagsString() +" Length: " +this.windowSize;}

    public String getFlagsString(){
        String[] flagsString = {"CWR", "ECE", "URG", "ACK", "PSH", "RST", "SYN", "FIN"};
        String flagReturn = "";
        boolean multiple = false;
        for (int i = 0; i < Integer.SIZE; i++) {
            if ((this.flags & (1 << i)) != 0) {
                if(multiple){
                    flagReturn += ", " + flagsString[i];
                } else {
                    flagReturn += flagsString[i];
                    multiple = true;
                }
            }
        }

        if(multiple) return "[" + flagReturn + "]";
        else return "";
    }

    //TODO
    public String getInfo(){
        return "";
    }
}
