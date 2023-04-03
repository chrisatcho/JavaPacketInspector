package org.example.Packets;

public class IPv6Packet implements CRPacket {

    char version;
    int trafficClass;
    int flowLabel;
    int payloadLength;
    int nextHeader;
    int hopLimit;
    String sourceIP;
    String destIP;
    String payload;

    /*
        Bits 0-3 - IP version
        Bits 4-11 - Traffic Class
        Bits 12-31 - Flow Label
        Bits 32-47 - Payload Length
        Bits 48-55 - Next Header
        Bits 56-63 - Hop Limit
        Bits 64-127 - Source Address
        Bits 128-191 - Destination Address
        Bits 192-... - Payload
    */

    public IPv6Packet(String payload) {

        this.version = payload.charAt(0);
        this.trafficClass = getIntFromHexString(payload.substring(1, 4));
        this.flowLabel = getIntFromHexString(payload.substring(4, 8));
        this.payloadLength = getIntFromHexString(payload.substring(8, 12));
        this.nextHeader = getIntFromHexString(payload.substring(12, 14));
        this.hopLimit = getIntFromHexString(payload.substring(14, 16));
        this.sourceIP = getIPv6FromHex(payload.substring(16, 48));
        this.destIP = getIPv6FromHex(payload.substring(48, 80));

        /*if (this.payloadLength > 0) {
            this.payload = payload.substring(80);
            System.out.println("Payload is " + this.payload);
        }*/
    }

    int getIntFromHexString(String twoHex) {
        return Integer.parseInt(String.valueOf(twoHex), 16);
    }




    String getIPv6FromHex(String hexIP) {
        String ipv6 = "";
        for (int i = 0; i < 8; ++i) {
            ipv6 += hexIP.substring(i * 4, i * 4 + 4);
            ipv6 += ":";
        }
        return ipv6.substring(0, ipv6.length() - 1);
    }


    @Override
    public String tostring() {
        return this.sourceIP + "->" + this.destIP + " IPv6, Length: " + this.payloadLength;

    }
}
