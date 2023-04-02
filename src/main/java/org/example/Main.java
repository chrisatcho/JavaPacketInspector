package org.example;

import java.io.IOException;
import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

// This project intakes packets using pcap4j, and then breaks them down into hex values, to be reconstructed into a custom packet class that can be used to analyze the packet.

public class Main {
    public static void main(String[] args) throws PcapNativeException, NotOpenException, InterruptedException {
        PcapNetworkInterface nif = getInterface(); //Get the interface object
        System.out.println(nif.getName());

        //Params for incoming packets
        int snapshotLength = 65536;
        int readTimeout = 50;

        // A handle is an abstraction of a pointer, referring to the interface.
        try (PcapHandle handle = nif.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS , readTimeout)) {
            handle.setFilter("ip", BpfProgram.BpfCompileMode.OPTIMIZE);

            // Wild lambda function
            PacketListener listener = packet -> {
                EthPacket ep = new EthPacket(packet.getRawData());
                System.out.println(ep.tostring());
            };

            handle.loop(0,listener);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with handle.");
        }
    }
    static PcapNetworkInterface getInterface() {
        PcapNetworkInterface device = null;

        try {
            device = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return device;
    }
}