package org.example.L4Packets;

import org.example.L4Packet;

import java.util.Map;

public class ICMPv4 extends L4Packet {
    int type;
    int code;
    String checksum;
    String body;
    String protocol;
    public ICMPv4(String payload, String protocol){
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
        successful = (this.type == 3 || this.type == 11 || this.type == 12 || this.type == 31) ? " Error" : " Success";
        return successful + " " + protocolMap.get(this.type);
    }

    @Override
    public String getShortString() {
        String successful;
        successful = (this.type == 3 || this.type == 11 || this.type == 12 || this.type == 31) ? " Error" : " Success";
        return successful + " " + protocolMap.get(this.type);
    }

    Map<Integer, String> protocolMap = Map.<Integer, String>ofEntries(
            Map.entry(0, "Echo Reply"),
            Map.entry(3, "Destination Unreachable"),
            Map.entry(4, "Source Quench"),
            Map.entry(5, "Redirect Message"),
            Map.entry(8, "Echo Request"),
            Map.entry(9, "Router Advertisement"),
            Map.entry(10, "Router Solicitation"),
            Map.entry(11, "Time Exceeded"),
            Map.entry(12, "Parameter Problem"),
            Map.entry(13, "Timestamp Request"),
            Map.entry(14, "Timestamp Reply"),
            Map.entry(15, "Information Request"),
            Map.entry(16, "Information Reply"),
            Map.entry(17, "Address Mask Request"),
            Map.entry(18, "Address Mask Reply"),
            Map.entry(30, "Traceroute"),
            Map.entry(31, "Datagram Conversion Error"),
            Map.entry(32, "Mobile Host Redirect"),
            Map.entry(33, "IPv6 Where-Are-You"),
            Map.entry(34, "IPv6 I-Am-Here"),
            Map.entry(35, "Mobile Registration Request"),
            Map.entry(36, "Mobile Registration Reply"),
            Map.entry(37, "Domain Name Request"),
            Map.entry(38, "Domain Name Reply"),
            Map.entry(39, "SKIP Algorithm Discovery Protocol"),
            Map.entry(40, "Photuris, Security failures"),
            Map.entry(41, "ICMP for experimental mobility protocols such as Seamoby [RFC4065]"),
            Map.entry(61, "Conservative selective acknowledgement"),
            Map.entry(62, "Experimental congestion encountered")
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
