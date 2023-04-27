package org.example.L4Packets;

import org.example.*;
import org.example.Utils.HexHelper;

public class TCPPacket extends L4Packet {
    String protocol;
    int sourcePort, destPort;
    int seqNum, ackNum;
    int dataOffset;
    boolean CWR, ECE, URG, ACK, PSH, RST, SYN, FIN;
    int windowSize;
    String checksum;
    int urgPointer;

    String payload;
    public TCPPacket(String payload, String protocol){
        this.protocol = protocol;
        this.sourcePort = HexHelper.hexToInt(payload.substring(0,4));
        this.destPort = HexHelper.hexToInt(payload.substring(4,8));

        this.seqNum = HexHelper.hexToInt(payload.substring(8,16));
        this.ackNum = HexHelper.hexToInt(payload.substring(16,24));

        this.dataOffset = HexHelper.hexToInt(payload.substring(24,25));

        String flags = HexHelper.hexToBinaryString(payload.substring(26,28));

        this.CWR = Integer.parseInt(String.valueOf(flags.charAt(0))) == 1;
        this.ECE = Integer.parseInt(String.valueOf(flags.charAt(1))) == 1;
        this.URG = Integer.parseInt(String.valueOf(flags.charAt(2))) == 1;
        this.ACK = Integer.parseInt(String.valueOf(flags.charAt(3))) == 1;
        this.PSH = Integer.parseInt(String.valueOf(flags.charAt(4))) == 1;
        this.RST = Integer.parseInt(String.valueOf(flags.charAt(5))) == 1;
        this.SYN = Integer.parseInt(String.valueOf(flags.charAt(6))) == 1;
        this.FIN = Integer.parseInt(String.valueOf(flags.charAt(7))) == 1;

        this.windowSize = HexHelper.hexToInt(payload.substring(28,32));

        this.checksum = payload.substring(32,36);
        this.urgPointer = HexHelper.hexToInt(payload.substring(36,40));

        if(this.windowSize > 5){
            this.payload = payload.substring(40);
        }

    }
    //TODO Still have to finish
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
