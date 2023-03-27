package org.example;

public class EthPacket {

    String srcMac,destMac, ethType, IPtype, payload;
    EtherType etherType;

    CRPacket packet;
    enum EtherType {
        IPv4, //0800
        IPv6, //86DD
        ARP, //0806
        AARP, // 0x809B - AppleTalk Address Resolution Protocol
        VLAN, // 0x8100
        PPPoE, // 0x8864
        MPLS, // 0x8847
        LLDP, // 0x88CC

    }
    EthPacket(byte[] bs){
        String s = "";
        int count = 0;
        for(byte b: bs){
            String hexToByte = byteToHex(b);
            s += hexToByte;
            count++;

            if(count == 6){
                this.destMac = s;
                s = "";
            } else if(count == 12){
                this.srcMac = s;
                s = "";
            } else if (count == 14){
                this.ethType = s;
                s = "";
            }
        }
        this.payload = s;

        if(this.ethType.equals("0800")){
            this.packet = new IPv4Packet(this.payload);
        } else if(this.ethType.equals("86DD")){
            //this.packet = new IPv6Packet(this.payload);
        }




        //System.out.println(formatMac(this.srcMac) + " " + formatMac(this.destMac) + " " + this.ethType + " " + this.payload.substring(2,6));
        System.out.println(this.packet.tostring());
        this.etherType = getEthertype(this.ethType);
    }

    EtherType getEthertype(String s){
        switch (s){
            case "0800":
                return EtherType.IPv4;
            case "86DD":
                return EtherType.IPv6;
            case "0806":
                return EtherType.ARP;
            case "8100":
                return EtherType.VLAN;
            case "8864":
                return EtherType.PPPoE;
            case "8847":
                return EtherType.MPLS;
            case "88CC":
                return EtherType.LLDP;
            case "809B":
                return EtherType.AARP;
            default:
                return null;
        }
    }


    String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF); // Convert byte to hex and mask to get the last 8 bits
        if (hex.length() == 1) {
            hex = "0" + hex; // Pad with a leading 0 for single-digit hex values
        }
        return hex.toUpperCase();
    }

    String formatMac (String address){
        String mac = "";
        for(int i = 0; i < 5; ++i){
            mac += address.substring(i * 2,i*2 + 2) + ":";
        }
        mac += address.substring(10,12);
        return mac;
    }


    String tostring(){
        return (formatMac(this.srcMac) + "->" + formatMac(this.destMac) + " " + this.etherType);
    }

}
