package org.example;

// Handling the Link Layer

public class L2Packet extends Packet {
    String destMac;
    String srcMac;
    String ethType;
    String payload;

    String nif;

    String rawHex;
    enum EtherType {
        IPV4, //0800
        IPV6, //86DD
        ARP, //0806
        LLDP, //88CC
        NOT_SUPPORTED
    }

    public L2Packet(byte[] bs, String nif){
        this.nif = nif;

        StringBuilder s = new StringBuilder();
        this.rawHex = "";
        int count = 0;
        for(byte b: bs){
            String hexToByte = byteToHex(b);
            s.append(hexToByte);
            count += 1;

            if(count == 6){
                this.rawHex += s.toString();
                this.destMac = formatMAC(s.toString());
                s = new StringBuilder();
            } else if(count == 12){
                this.rawHex += s.toString();
                this.srcMac = formatMAC(s.toString());
                s = new StringBuilder();
            } else if (count == 14){
                this.rawHex += s.toString();
                this.ethType = getEthType(s.toString());
                s = new StringBuilder();
            }
        }
        this.rawHex += s.toString();
        this.payload = s.toString();
    }

    public String getEthType(String s) {
        return switch (s) {
            case "0800" -> String.valueOf(EtherType.IPV4);
            case "86DD" -> String.valueOf(EtherType.IPV6);
            case "0806" -> String.valueOf(EtherType.ARP);
            case "88CC" -> String.valueOf(EtherType.LLDP);
            default     -> String.valueOf(EtherType.NOT_SUPPORTED);
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

    public void printAll(boolean verbose) {
        if(verbose) System.out.println(this.getString());
        else System.out.print(this.getString());
    }

    public String getString(){return "Ethernet II, Src: " + this.srcMac + ", Dst: " + this.destMac;};

    public String getRawHex(){return this.rawHex;}


}
