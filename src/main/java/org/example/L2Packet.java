package org.example;

// Handling the Link Layer

public class L2Packet extends Packet {
    String destMac;
    String srcMac;
    EtherType ethType;
    String payload;

    enum EtherType {
        IPV4, //0800
        IPV6, //86DD
        ARP, //0806
        LLDP, //88CC
        NOT_SUPPORTED
    }

    public L2Packet(byte[] bs){
        StringBuilder s = new StringBuilder();
        int count = 0;
        for(byte b: bs){
            String hexToByte = byteToHex(b);
            s.append(hexToByte);
            count += 1;

            if(count == 6){
                this.destMac = formatMAC(s.toString());
                s = new StringBuilder();
            } else if(count == 12){
                this.srcMac = formatMAC(s.toString());
                s = new StringBuilder();
            } else if (count == 14){
                this.ethType = getEthType(s.toString());
                s = new StringBuilder();
            }
        }
        this.payload = s.toString();
    }

    public EtherType getEthType(String s) {
        return switch (s) {
            case "0800" -> EtherType.IPV4;
            case "86DD" -> EtherType.IPV6;
            case "0806" -> EtherType.ARP;
            default     -> EtherType.NOT_SUPPORTED;
        };
    }

    private static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        return hex;
    }

    public String formatMAC(String s) {
        return s.substring(0, 2) + ":" + s.substring(2, 4) + ":" + s.substring(4, 6) + ":" + s.substring(6, 8) + ":" + s.substring(8, 10) + ":" + s.substring(10, 12);
    }

    public void printAll() {
        System.out.println("Ethernet II, Src: " + this.srcMac + " , Dst: " + this.destMac);
    }
    public void printRaw() {
        System.out.println(this.destMac + this.srcMac + this.ethType + this.payload);
    }
}
