package org.example;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

public class Main {
    public static void main(String[] args) {
        PcapNetworkInterface nif = getInterface(); //Get the interface object
        System.out.println(nif.getName());

        boolean printAll = true;

        //Params for incoming packets
        int snapshotLength = 65536;
        int readTimeout = 50;
        AtomicInteger count = new AtomicInteger();

        // A handle is an abstraction of a pointer, referring to the interface.
        try (PcapHandle handle = nif.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS , readTimeout)) {
            handle.setFilter("ip", BpfProgram.BpfCompileMode.OPTIMIZE);

            PacketListener listener = packet -> {
                count.getAndIncrement();
                byte[] data = packet.getRawData();
                L2Packet Ethernet = PacketFactory.parseL2Packet(data);
                L3Packet l3 = PacketFactory.parseL3Packet(Ethernet);
                if (printAll) {
                    System.out.println("Frame " + count.get() + ": "
                            + data.length + " bytes captured (" + data.length * 8 + " bits) on interface "
                            + nif.getName());

                    Ethernet.printAll();
                    if (l3 != null) l3.printAll();
                }
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