package org.example;

public class IPv4Packet implements CRPacket {

    char version;
    char headerLength;

    int totalLength;
    int identification;

    String flags;
    int fragmentOffset;

    /*
        Bits 0-3 - IPversion
        Bits 4-7 - Header Length
        Bits 8-13 - DSCP
        Bits 14-15 - ECN
        Bits 16-31 - Total Length
        Bits 32-47 - Identification
        Bits 48-50 - Flags- bit 0: Reserved; must be zero, bit 1: Don't Fragment (DF), bit 2: More Fragments (MF)
        Bits 51-63 - Fragment Offset
        Bits 64-71 - TTL
        Bits 72-79 - Protocol
        Bits 80-95 - Header Checksum
        Bits 96-127 - Source Address
        Bits 128-159 - Destination Address
        Bits 160-192 - May be used if the header length > 5; Octets can be 20-56

    */

    IPv4Packet(String payload){
        this.version = payload.charAt(0);
        this.headerLength = payload.charAt(1);

        this.totalLength = getIntFromHexString(payload.substring(4,8));
        this.identification = getIntFromHexString(payload.substring(8,12));

        this.flags = getFlags(payload.substring(12,16));
    }

    int getIntFromSingleHex(char hex){
        return Integer.parseInt(String.valueOf(hex), 16);
    }
    int getIntFromHexString(String twoHex){
        return Integer.parseInt(String.valueOf(twoHex),16);
    }

    String getFlags(String hex){
        return hexToBin(hex).substring(0,3);
    }
    public String tostring(){
        return " IPv4, length: " + getIntFromSingleHex(this.headerLength) + " with a total length of " + this.totalLength + ". Identification = " + this.identification + " Flags = " + this.flags;
    }

    /*
    LMAO THIS IS THE MOST UGLY FUNCTION BUT ITS FAST AND EFFECTIVE
     */
    private String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
}
