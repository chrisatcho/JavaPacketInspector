package org.example.Packets;
import org.example.HexHelper;
import org.example.Packets.Protocols.*;

public class IPv4Header extends CRPackets {
    int version; //bits 0-3
    int headerLength; //4-7
    String DSP; //Bits8-13
    String ECN; //bits 14-15
    int totalLength; //bits 16-31
    int identification;//bits 32-47
    String flags;//bits 48-50
    int fragmentOffset;//bits 51-63
    int TTL;//bits 64-71
    int protocol;//bits 72-79
    int checkSum;//bits 80-95

    /*
    Both stored in superclass
    Source IP - bits 96-127
    Dest IP - bits 128-159
     */

    Protocol protocolPacket;

    public IPv4Header(String payload){
        super(HexHelper.getIPFromHex(payload.substring(24,32)), HexHelper.getIPFromHex(payload.substring(32,40)));

        this.version = HexHelper.getIntFromHexString(payload.substring(0,1));
        this.headerLength = HexHelper.getIntFromHexString(payload.substring(1,2));

        this.totalLength = HexHelper.getIntFromHexString(payload.substring(4,8));
        this.identification = HexHelper.getIntFromHexString(payload.substring(8,12));
        this.TTL = HexHelper.getIntFromHexString(payload.substring(16,18));
        this.protocol = HexHelper.getIntFromHexString(payload.substring(18,20));

        System.out.println(this.protocol);
        if(this.protocol == 6){
            this.protocolPacket = new TCP(payload.substring(44));
        } else if(this.protocol == 17){
            //this.protocolPacket = newUDP(payload.substring(44));
        }
    }
}
