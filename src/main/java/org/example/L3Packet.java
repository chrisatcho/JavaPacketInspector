package org.example;

import java.util.Map;

import static java.util.Map.entry;

// This packet handles the Network layer, but also the Data Link layer in some situations

abstract class L3Packet extends Packet {

    public abstract void parseVariables(String s);
    public abstract void printAll(String srcIPHostname, String destIPHostname, boolean verbose);
    public abstract void printPayload();
    public abstract void printSrcIP();
    public abstract void printDestIP();
    public abstract void printProtocol();
    public abstract String getPayload();
    public abstract String getSrcIP();
    public abstract String getDestIP();
    public abstract String getProtocol();

    public String matchProtocol(String s) {
        Integer a = Integer.parseInt(s, 16);
        return protocolMap.get(a);
    }
    public abstract String getString(String srcIPHostname, String destIPHostname);

    public abstract String getShortString(String srcIPHostname, String destIPHostname);

    //This map could potentially be moved elsewhere for better memory management.
    Map<Integer, String> protocolMap = Map.<Integer, String>ofEntries(
            entry(0, "HOPOPT"),  // IPv6 Hop-by-Hop Option
            entry(1, "ICMPv4"),  // Internet Control Message
            entry(2, "IGMP"),  // Internet Group Management
            entry(3, "GGP"),  // Gateway-to-Gateway
            entry(4, "IPv4"),  // IPv4 encapsulation
            entry(5, "ST"),  // Stream
            entry(6, "TCP"),  // Transmission Control
            entry(7, "CBT"),  // CBT
            entry(8, "EGP"),  // Exterior Gateway Protocol
            entry(9, "IGP"),  // any private interior gateway (used by Cisco for their IGRP)
            entry(10, "BBN-RCC-MON"),  // BBN RCC Monitoring
            entry(11, "NVP-II"),  // Network Voice Protocol
            entry(12, "PUP"),  // PUP
            entry(13, "ARGUS (deprecated)"),  // ARGUS
            entry(14, "EMCON"),  // EMCON
            entry(15, "XNET"),  // Cross Net Debugger
            entry(16, "CHAOS"),  // Chaos
            entry(17, "UDP"),  // User Datagram
            entry(18, "MUX"),  // Multiplexing
            entry(19, "DCN-MEAS"),  // DCN Measurement Subsystems
            entry(20, "HMP"),  // Host Monitoring
            entry(21, "PRM"),  // Packet Radio Measurement
            entry(22, "XNS-IDP"),  // XEROX NS IDP
            entry(23, "TRUNK-1"),  // Trunk-1
            entry(24, "TRUNK-2"),  // Trunk-2
            entry(25, "LEAF-1"),  // Leaf-1
            entry(26, "LEAF-2"),  // Leaf-2
            entry(27, "RDP"),  // Reliable Data Protocol
            entry(28, "IRTP"),  // Internet Reliable Transaction
            entry(29, "ISO-TP4"),  // ISO Transport Protocol Class 4
            entry(30, "NETBLT"),  // Bulk Data Transfer Protocol
            entry(31, "MFE-NSP"),  // MFE Network Services Protocol
            entry(32, "MERIT-INP"),  // MERIT Internodal Protocol
            entry(33, "DCCP"),  // Datagram Congestion Control Protocol
            entry(34, "3PC"),  // Third Party Connect Protocol
            entry(35, "IDPR"),  // Inter-Domain Policy Routing Protocol
            entry(36, "XTP"),  // XTP
            entry(37, "DDP"),  // Datagram Delivery Protocol
            entry(38, "IDPR-CMTP"),  // IDPR Control Message Transport Proto
            entry(39, "TP++"),  // TP++ Transport Protocol
            entry(40, "IL"),  // IL Transport Protocol
            entry(41, "IPv6"),  // IPv6 encapsulation
            entry(42, "SDRP"),  // Source Demand Routing Protocol
            entry(43, "IPv6-Route"),  // Routing Header for IPv6
            entry(44, "IPv6-Frag"),  // Fragment Header for IPv6
            entry(45, "IDRP"),  // Inter-Domain Routing Protocol
            entry(46, "RSVP"),  // Reservation Protocol
            entry(47, "GRE"),  // Generic Routing Encapsulation
            entry(48, "DSR"),  // Dynamic Source Routing Protocol
            entry(49, "BNA"),  // BNA
            entry(50, "ESP"),  // Encap Security Payload
            entry(51, "AH"),  // Authentication Header
            entry(52, "I-NLSP"),  // Integrated Net Layer Security  TUBA
            entry(53, "SWIPE (deprecated)"),  // IP with Encryption
            entry(54, "NARP"),  // NBMA Address Resolution Protocol
            entry(55, "MOBILE"),  // IP Mobility
            entry(56, "TLSP"),  // Transport Layer Security Protocol using Kryptonet key management
            entry(57, "SKIP"),  // SKIP
            entry(58, "ICMPv6"),  // ICMP for IPv6
            entry(59, "IPv6-NoNxt"),  // No Next Header for IPv6
            entry(60, "IPv6-Opts"),  // Destination Options for IPv6
            entry(61, "anyInternal"), // any host internal protocol
            entry(62, "CFTP"),  // CFTP
            entry(63, "anyLocal"),  // any local network
            entry(64, "SAT-EXPAK"),  // SATNET and Backroom EXPAK
            entry(65, "KRYPTOLAN"),  // Kryptolan
            entry(66, "RVD"),  // MIT Remote Virtual Disk Protocol
            entry(67, "IPPC"),  // Internet Pluribus Packet Core
            entry(68, "anyDist"),  //any distributed file system
            entry(69, "SAT-MON"),  // SATNET Monitoring
            entry(70, "VISA"),  // VISA Protocol
            entry(71, "IPCV"),  // Internet Packet Core Utility
            entry(72, "CPNX"),  // Computer Protocol Network Executive
            entry(73, "CPHB"),  // Computer Protocol Heart Beat
            entry(74, "WSN"),  // Wang Span Network
            entry(75, "PVP"),  // Packet Video Protocol
            entry(76, "BR-SAT-MON"),  // Backroom SATNET Monitoring
            entry(77, "SUN-ND"),  // SUN ND PROTOCOL-Temporary
            entry(78, "WB-MON"),  // WIDEBAND Monitoring
            entry(79, "WB-EXPAK"),  // WIDEBAND EXPAK
            entry(80, "ISO-IP"),  // ISO Internet Protocol
            entry(81, "VMTP"),  // VMTP
            entry(82, "SECURE-VMTP"),  // SECURE-VMTP
            entry(83, "VINES"),  // VINES
            entry(84, "IPTM"),  // Internet Protocol Traffic Manager
            entry(85, "NSFNET-IGP"),  // NSFNET-IGP
            entry(86, "DGP"),  // Dissimilar Gateway Protocol
            entry(87, "TCF"),  // TCF
            entry(88, "EIGRP"),  // EIGRP
            entry(89, "OSPFIGP"),  // OSPFIGP
            entry(90, "Sprite-RPC"),  // Sprite RPC Protocol
            entry(91, "LARP"),  // Locus Address Resolution Protocol
            entry(92, "MTP"),  // Multicast Transport Protocol
            entry(93, "AX.25"),  // AX.25 Frames
            entry(94, "IPIP"),  // IP-within-IP Encapsulation Protocol
            entry(95, "MICP (deprecated)"),  // Mobile Internetworking Control Pro.
            entry(96, "SCC-SP"),  // Semaphore Communications Sec. Pro.
            entry(97, "ETHERIP"),  // Ethernet-within-IP Encapsulation
            entry(98, "ENCAP"),  // Encapsulation Header
            entry(99, "anyPrivate"),  // any private encryption scheme
            entry(100, "GMTP"),  // GMTP
            entry(101, "IFMP"),  // Ipsilon Flow Management Protocol
            entry(102, "PNNI"),  // PNNI over IP
            entry(103, "PIM"),  // Protocol Independent Multicast
            entry(104, "ARIS"),  // ARIS
            entry(105, "SCPS"),  // SCPS
            entry(106, "QNX"),  // QNX
            entry(107, "A/N"),  // Active Networks
            entry(108, "IPComp"),  // IP Payload Compression Protocol
            entry(109, "SNP"),  // Sitara Networks Protocol
            entry(110, "Compaq-Peer"),  // Compaq Peer Protocol
            entry(111, "IPX-in-IP"),  // IPX in IP
            entry(112, "VRRP"),  // Virtual Router Redundancy Protocol
            entry(113, "PGM"),  // PGM Reliable Transport Protocol
            entry(114, "any0Hop"),  // any 0-hop protocol
            entry(115, "L2TP"), // Layer Two Tunneling Protocol
            entry(116, "DDX"), // D-II Data Exchange (DDX)
            entry(118, "STP"), // Schedule Transfer Protocol
            entry(119, "SRP"), // SpectraLink Radio Protocol
            entry(120, "UTI"), // UTI
            entry(121, "SMP"), // Simple Message Protocol
            entry(122, "SM (deprecated)"), // Simple Multicast Protocol
            entry(123, "PTP"), // Performance Transparency Protocol
            entry(124, "ISIS over IPv4"), //
            entry(125, "FIRE"), //
            entry(126, "CRTP"), // Combat Radio Transport Protocol
            entry(127, "CRUDP"), // Combat Radio User Datagram
            entry(128, "SSCOPMCE"), //
            entry(129, "IPLT"), //
            entry(130, "SPS"), // Secure Packet Shield
            entry(131, "PIPE"), // Private IP Encapsulation within IP
            entry(132, "SCTP"), // Stream Control Transmission Protocol
            entry(133, "FC"), // Fibre Channel
            entry(134, "RSVP-E2E-IGNORE"), //
            entry(135, "Mobility Header"), //
            entry(136, "UDPLite"), //
            entry(137, "MPLS-in-IP"), //
            entry(138, "manet"), // MANET Protocols
            entry(139, "HIP"), // Host Identity Protocol
            entry(140, "Shim6"), // Shim6 Protocol
            entry(141, "WESP"), // Wrapped Encapsulating Security Payload
            entry(142, "ROHC"), // Robust Header Compression
            entry(143, "Ethernet") // Ethernet
            // 144,"AGGFRAG" // AGGFRAG encapsulation payload for ESP
            // 145-252, Unassigned
            // 253,,Use for experimentation and testing
            // 254,,Use for experimentation and testing
            //entry(255, "Reserved"), // 
    );
}
