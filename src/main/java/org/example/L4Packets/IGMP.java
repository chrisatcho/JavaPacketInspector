package org.example.L4Packets;

import org.example.L4Packet;

public class IGMP extends L4Packet {
    String protocol;
    String type;
    String payload;
    public IGMP(String payload, String protocol){
        this.protocol = protocol;
        this.type = payload.substring(0,2);
        this.payload = payload;
    }
    String getIGMPType(String hex){
        return switch (hex){
            case "94" -> "IGMPv3";
            case "11" -> "IGMPv1 Membership Query";
            case "12" -> "IGMPv1 Membership Report";
            case "13" -> "IGMPv1 Leave Group";
            case "16" -> "IGMPv2 Membership Query";
            case "17" -> "IGMPv2 Membership Report";
            case "18" -> "IGMPv2 Leave Group";
            default -> "IGMP";
        };
    }
    @Override
    public void printPayload() {
        System.out.println(this.payload);
    }
    @Override
    public void printAll(boolean verbose) {

    }
    @Override
    public int getSourcePort() {
        return -1;
    }
    @Override
    public int getDestPort() {
        return -1;
    }
    @Override
    public String getString() {
        return getIGMPType(this.type);
    }
    @Override
    public String getShortString() {
        return getIGMPType(this.type);
    }
}
