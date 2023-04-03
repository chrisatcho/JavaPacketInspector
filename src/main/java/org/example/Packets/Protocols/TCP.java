package org.example.Packets.Protocols;

import org.example.HexHelper;
public class TCP extends Protocol{
    String protocol = "TCP";
    int seqNumber; //bits 31-63
    int ackNumber; //bits 64-95
    int dataOffset; //bits 96-99
    //Reserved bits 100-103
    boolean CWR;//bit 104 - Congestion Window Reduced
    boolean ECE;//bit 105 - ECN echo
    boolean URG;//bit 106 - Urgent pointer field is significant
    boolean ACK;//bit 107 - Acknowledgement field is significant
    boolean PSH;//bit 108 - Push function.
    boolean RST;//bit 109 - Reset connection
    boolean SYN;//bit 110 - Synchronise sequence numbers.
    boolean FIN;//bit 111 - Last packet from sender
    int windowSize; //bits 112-127
    //Checksum bits 128-143
    //Urgent Pointer - If URG is true - bits 144-159

    public TCP(String payload){
        super(HexHelper.getIntFromHexString(payload.substring(0,4)), HexHelper.getIntFromHexString(payload.substring(4,8)));
    }
}
