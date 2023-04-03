package org.example.Packets;

public class ARPPacket implements CRPacket {

    int hardwareType;
    int protocolType;
    int hardwareAddrLength;
    int protocolAddrLength;
    int operation;
    String senderHardwareAddr;
    String senderProtocolAddr;
    String targetHardwareAddr;
    String targetProtocolAddr;

    /*
        Bits 0-15 - Hardware Type
        Bits 16-31 - Protocol Type
        Bits 32-39 - Hardware Address Length
        Bits 40-47 - Protocol Address Length
        Bits 48-63 - Operation
        Bits 64-111 - Sender Hardware Address
        Bits 112-159 - Sender Protocol Address
        Bits 160-207 - Target Hardware Address
        Bits 208-255 - Target Protocol Address
    */

    public ARPPacket(String payload) {
        this.hardwareType = getIntFromHexString(payload.substring(0, 4));
        this.protocolType = getIntFromHexString(payload.substring(4, 8));
        this.hardwareAddrLength = getIntFromSingleHex(payload.charAt(8));
        this.protocolAddrLength = getIntFromSingleHex(payload.charAt(9));
        this.operation = getIntFromHexString(payload.substring(10, 12));
        this.senderHardwareAddr = getMACFromHex(payload.substring(12, 24));
        this.senderProtocolAddr = getIPFromHex(payload.substring(24, 32));
        this.targetHardwareAddr = getMACFromHex(payload.substring(32, 44));
        this.targetProtocolAddr = getIPFromHex(payload.substring(44, 52));
    }

    int getIntFromSingleHex(char hex) {
        return Integer.parseInt(String.valueOf(hex), 16);
    }

    int getIntFromHexString(String twoHex) {
        return Integer.parseInt(String.valueOf(twoHex), 16);
    }

    String getMACFromHex(String hexMAC) {
        String mac = "";
        for (int i = 0; i < 6; ++i) {
            mac += hexMAC.substring(i * 2, i * 2 + 2);
            mac += ":";
        }
        return mac.substring(0, mac.length() - 1);
    }

    String getIPFromHex(String hexIP) {
        String ip = "";
        for (int i = 0; i < 4; ++i) {
            ip += getIntFromHexString(hexIP.substring(i * 2, i * 2 + 2));
            ip += ".";
        }
        return ip.substring(0, ip.length() - 1);
    }


    @Override
    public String tostring() {
        return "ARP Packet: " + senderProtocolAddr + " is asking for " + targetProtocolAddr + " (" + targetHardwareAddr + ")";

    }
}
