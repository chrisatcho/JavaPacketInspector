package org.example;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        //Get the interface object
        PcapNetworkInterface nif = getInterface();
        System.out.println(nif.getName());

        // Get filter information
        String[] filterList = getFilterParams();
        Filter filter = new Filter(filterList[0], filterList[1], filterList[2], filterList[3], filterList[4]);

        //Params for incoming packets
        int snapshotLength = 65536;
        int readTimeout = 50;
        AtomicInteger count = new AtomicInteger();

        //Output to a file
        OutputToFile output = OutputToFile.getOutputToFile();

        // A handle is an abstraction of a pointer, referring to the interface.
        try (PcapHandle handle = nif.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS , readTimeout)) {
            handle.setFilter("ip", BpfProgram.BpfCompileMode.OPTIMIZE);

            PacketListener listener = packet -> {
                count.getAndIncrement();
                byte[] data = packet.getRawData();
                L2Packet Ethernet = PacketFactory.parseL2Packet(data);
                L3Packet l3 = PacketFactory.parseL3Packet(Ethernet);
                L4Packet l4 = PacketFactory.parseL4Packet(l3, Ethernet);

                if (filter.check(l3) && filter.check(l4)) {
                    System.out.println("Frame " + count.get() + ": "
                            + data.length + " bytes captured (" + data.length * 8 + " bits) on interface "
                            + nif.getName());

                    Ethernet.printAll();
                    l3.printAll();
                    l4.printAll();
                    System.out.println("-------------------");

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
    /*
    Returns a list of parameters for the filter
    Index: 0 - Source IP
    Index: 1 - Destination IP
    Index: 2 - Source Port
    Index: 3 - Destination Port
    Index: 4 - Protocol
     */
    static String[] getFilterParams() {
        String input;
        Scanner scanner = new Scanner(System.in);
        String[] params = {"0.0.0.0/32", "0.0.0.0/32", "0", "0", ""};

        System.out.println("Enter the following parameters for the filter: (Press enter to skip a parameter)");
        System.out.println("Source IP (CIDR block): ");
        input = scanner.nextLine();
        if (!input.equals("")) params[0] = input;

        System.out.println("Destination IP (CIDR block): ");
        input = scanner.nextLine();
        if (!input.equals("")) params[1] = input;

        System.out.println("Source Port: ");
        input = scanner.nextLine();
        if (!input.equals("")) params[2] = input;

        System.out.println("Destination Port: ");
        input = scanner.nextLine();
        if (!input.equals("")) params[3] = input;

        System.out.println("Protocol: ");
        input = scanner.nextLine();
        if (!input.equals("")) params[4] = input;

        return params;
    }
}