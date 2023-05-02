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
        WOL, //0842
        AVTP, //22F0
        IETF_TRILL, //22F3
        SR_ETH, //22EA
        DEC_MOP_RC, //6002
        DECnet, //6003
        DEC_LAT, //6004
        RARP, //8035
        APPLETALK, //809B
        AARP, //80F3
        VLAN, //8100
        SLPP, //8102
        VLACP, //8103
        IPX, //8137
        QNET, //8204
        ETHERNET_FLOW_CONTROL, //8808
        ETHERNET_SLOW, //8809
        COBRANET, //8819
        MPLS, //8847
        MPLS_MULTI, //8848
        PPPOE_DISCOVERY, //8863
        PPPOE_SESSION, //8864
        HOMEPLUG, //887B
        EAP_OVER_LAN, //888E
        PROFINET, //8892
        HYPERSCSI, //889A
        AoE, //88A2
        ETHERCAT, //88A4
        SVLAN, //88A8
        ETHERNET_POWERLINK, //88AB
        GOOSE, //88B8
        GSE, //88B9
        SV, //88BA
        MIKROTIK, //88BF
        LLDP, //88CC
        SERCOS, //88CD
        HOMEPLUG_GP, //88E1
        MRP, //88E3
        MACSEC, //88E5
        PBB, //88E7
        PTP, //88F7
        NC_SI, //88F8
        PRP, //88FB
        CFM, //8902
        FCOE, //8906
        FCOE_INIT, //8914
        ROCE, //8915
        TTE, //891D
        IEEE1905_1, //893a
        HSR, //892F
        CONFIG_TEST, //9000
        REDUNDANCY_TAG, //F1C1
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
        return switch (s.toUpperCase()) {
            case "0800" -> String.valueOf(EtherType.IPV4); // Internet Protocol version 4 (IPv4)
            case "0806" -> String.valueOf(EtherType.ARP); // Address Resolution Protocol (ARP)
            case "0842" -> String.valueOf(EtherType.WOL); // Wake-on-LAN
            case "22F0" -> String.valueOf(EtherType.AVTP); // Audio Video Transport Protocol (AVTP)
            case "22F3" -> String.valueOf(EtherType.IETF_TRILL); // IETF TRILL Protocol
            case "22EA" -> String.valueOf(EtherType.SR_ETH); // Stream Reservation Protocol
            case "6002" -> String.valueOf(EtherType.DEC_MOP_RC); // DEC MOP RC
            case "6003" -> String.valueOf(EtherType.DECnet); // DECnet Phase IV, DNA Routing
            case "6004" -> String.valueOf(EtherType.DEC_LAT); // DEC LAT
            case "8035" -> String.valueOf(EtherType.RARP); // Reverse Address Resolution Protocol (RARP)
            case "809B" -> String.valueOf(EtherType.APPLETALK); // AppleTalk (Ethertalk)
            case "80F3" -> String.valueOf(EtherType.AARP); // AppleTalk Address Resolution Protocol (AARP)
            case "8100" -> String.valueOf(EtherType.VLAN); // VLAN-tagged frame (IEEE 802.1Q) and Shortest Path Bridging IEEE 802.1aq with NNI compatibility
            case "8102" -> String.valueOf(EtherType.SLPP); // Simple Loop Prevention Protocol (SLPP)
            case "8103" -> String.valueOf(EtherType.VLACP); // Virtual Link Aggregation Control Protocol (VLACP)
            case "8137" -> String.valueOf(EtherType.IPX); // IPX
            case "8204" -> String.valueOf(EtherType.QNET); // QNX Qnet
            case "86DD" -> String.valueOf(EtherType.IPV6); // Internet Protocol Version 6 (IPv6)
            case "8808" -> String.valueOf(EtherType.ETHERNET_FLOW_CONTROL); // Ethernet flow control
            case "8809" -> String.valueOf(EtherType.ETHERNET_SLOW); // Ethernet Slow Protocols such as the Link Aggregation Control Protocol (LACP)
            case "8819" -> String.valueOf(EtherType.COBRANET); // CobraNet
            case "8847" -> String.valueOf(EtherType.MPLS); // MPLS unicast
            case "8848" -> String.valueOf(EtherType.MPLS_MULTI); // MPLS multicast
            case "8863" -> String.valueOf(EtherType.PPPOE_DISCOVERY); // PPPoE Discovery Stage
            case "8864" -> String.valueOf(EtherType.PPPOE_SESSION); // PPPoE Session Stage
            case "887B" -> String.valueOf(EtherType.HOMEPLUG); // HomePlug 1.0 MME
            case "888E" -> String.valueOf(EtherType.EAP_OVER_LAN); // EAP over LAN (IEEE 802.1X)
            case "8892" -> String.valueOf(EtherType.PROFINET); // PROFINET Protocol
            case "889A" -> String.valueOf(EtherType.HYPERSCSI); // HyperSCSI (SCSI over Ethernet)
            case "88A2" -> String.valueOf(EtherType.AoE); // ATA over Ethernet
            case "88A4" -> String.valueOf(EtherType.ETHERCAT); // EtherCAT Protocol
            case "88A8" -> String.valueOf(EtherType.SVLAN); // Service VLAN tag identifier (S-Tag) on Q-in-Q tunnel.
            case "88AB" -> String.valueOf(EtherType.ETHERNET_POWERLINK); // Ethernet Powerlink
            case "88B8" -> String.valueOf(EtherType.GOOSE); // GOOSE (Generic Object Oriented Substation event)
            case "88B9" -> String.valueOf(EtherType.GSE); // GSE (Generic Substation Events) Management Services
            case "88BA" -> String.valueOf(EtherType.SV); // SV (Sampled Value Transmission)
            case "88BF" -> String.valueOf(EtherType.MIKROTIK); // MikroTik RoMON
            case "88CC" -> String.valueOf(EtherType.LLDP); // Link Layer Discovery Protocol (LLDP)
            case "88CD" -> String.valueOf(EtherType.SERCOS); // SERCOS III
            case "88E1" -> String.valueOf(EtherType.HOMEPLUG_GP); // HomePlug Green PHY
            case "88E3" -> String.valueOf(EtherType.MRP); // Media Redundancy Protocol (IEC62439-2)
            case "88E5" -> String.valueOf(EtherType.MACSEC); // IEEE 802.1AE MAC security (MACsec)
            case "88E7" -> String.valueOf(EtherType.PBB); // Provider Backbone Bridges (PBB) (IEEE 802.1ah)
            case "88F7" -> String.valueOf(EtherType.PTP); // Precision Time Protocol (PTP) over IEEE 802.3 Ethernet
            case "88F8" -> String.valueOf(EtherType.NC_SI); // NC-SI
            case "88FB" -> String.valueOf(EtherType.PRP); // Parallel Redundancy Protocol (PRP)
            case "8902" -> String.valueOf(EtherType.CFM); // IEEE 802.1ag Connectivity Fault Management (CFM) Protocol / ITU-T Recommendation Y.1731 (OAM)
            case "8906" -> String.valueOf(EtherType.FCOE); // Fibre Channel over Ethernet (FCoE)
            case "8914" -> String.valueOf(EtherType.FCOE_INIT); // FCoE Initialization Protocol
            case "8915" -> String.valueOf(EtherType.ROCE); // RDMA over Converged Ethernet (RoCE)
            case "891D" -> String.valueOf(EtherType.TTE); // TTEthernet Protocol Control Frame (TTE)
            case "893a" -> String.valueOf(EtherType.IEEE1905_1); // 1905.1 IEEE Protocol
            case "892F" -> String.valueOf(EtherType.HSR); // High-availability Seamless Redundancy (HSR)
            case "9000" -> String.valueOf(EtherType.CONFIG_TEST); // Ethernet Configuration Testing Protocol
            case "F1C1" -> String.valueOf(EtherType.REDUNDANCY_TAG); // Redundancy Tag (IEEE 802.1CB Frame Replication and Elimination for Reliability)
            default -> String.valueOf(EtherType.NOT_SUPPORTED);
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

    public String getString(){return "Ethernet II, Src: " + this.srcMac + ", Dst: " + this.destMac ;};

    public String getRawHex(){return this.rawHex;}


}
