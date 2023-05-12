package org.example.L4Packets;

import org.example.L4Packet;

import java.util.Map;

public class ICMPv6 extends L4Packet {
    int type;
    int code;
    String checksum;
    String body;
    String protocol;
    public ICMPv6(String payload, String protocol){
        this.protocol = protocol;
        this.type = Integer.parseInt(payload.substring(0,2), 16);
        this.code = Integer.parseInt(payload.substring(2,4), 16);
        this.checksum = payload.substring(4, 8);
        this.body = payload.substring(8);
    }
    @Override
    public void printPayload() {
        System.out.print(this.body);
    }

    @Override
    public void printAll(boolean verbose) {
        System.out.print(this.getString());
    }

    @Override
    public String getString() {
        String successful;
        successful = (this.type >= 1 && this.type <= 4) ? " Error" : " Success";
        return successful + " " + protocolMap.get(this.type);
    }

    @Override
    public String getShortString() {
        String successful;
        successful = (this.type >= 1 && this.type <= 4) ? " Error" : " Success";
        return successful + " " + protocolMap.get(this.type);
    }

    Map<Integer, String> protocolMap = Map.<Integer, String>ofEntries(
            Map.entry(1, "Destination Unreachable"),
            Map.entry(2, "Packet Too Big"),
            Map.entry(3, "Time Exceeded"),
            Map.entry(4, "Parameter Problem"),
            Map.entry(100, "Private experimentation"),
            Map.entry(101, "Private experimentation"),
            Map.entry(127, "Reserved for expansion of ICMPv6 error messages"),
            Map.entry(128, "Echo Request"),
            Map.entry(129, "Echo Reply"),
            Map.entry(130, "Multicast Listener Query"),
            Map.entry(131, "Multicast Listener Report"),
            Map.entry(132, "Multicast Listener Done"),
            Map.entry(133, "Router Solicitation"),
            Map.entry(134, "Router Advertisement"),
            Map.entry(135, "Neighbor Solicitation"),
            Map.entry(136, "Neighbor Advertisement"),
            Map.entry(137, "Redirect Message"),
            Map.entry(138, "Router Renumbering"),
            Map.entry(139, "ICMP Node Information Query"),
            Map.entry(140, "ICMP Node Information Response"),
            Map.entry(141, "Inverse Neighbor Discovery Solicitation Message"),
            Map.entry(142, "Inverse Neighbor Discovery Advertisement Message"),
            Map.entry(143, "Version 2 Multicast Listener Report"),
            Map.entry(144, "Home Agent Address Discovery Request"),
            Map.entry(145, "Home Agent Address Discovery Reply"),
            Map.entry(146, "Mobile Prefix Solicitation"),
            Map.entry(147, "Mobile Prefix Advertisement"),
            Map.entry(148, "Certification Path Solicitation"),
            Map.entry(149, "Certification Path Advertisement"),
            Map.entry(150, "ICMP messages utilized by experimental mobility protocols such as Seamoby"),
            Map.entry(151, "Multicast Router Advertisement"),
            Map.entry(152, "Multicast Router Solicitation"),
            Map.entry(153, "Multicast Router Termination"),
            Map.entry(154, "FMIPv6 Messages"),
            Map.entry(155, "RPL Control Message"),
            Map.entry(200, "Private experimentation"),
            Map.entry(201, "Private experimentation"),
            Map.entry(255, "Reserved for expansion of ICMPv6 informational messages")
    );

    @Override
    public int getSourcePort(){
        return -1;
    }

    @Override
    public int getDestPort(){
        return -1;
    }
}
