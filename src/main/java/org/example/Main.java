package org.example;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Get the interface object
        PcapNetworkInterface nif = getInterface();
        System.out.println(nif.getName());

        // Get filter information
        String[] filterList = getFilterParams();
        Filter filter = new Filter(filterList[0], filterList[1], filterList[2], filterList[3], filterList[4], filterList[5]);

        //Params for incoming packets
        int snapshotLength = 65536;
        int readTimeout = 50;

        //Output to a file
        OutputToFile output = OutputToFile.getOutputToFile();

        //Buffer for packets so none are missed
        PacketBuffer buffer = new PacketBuffer(filter, output);

        // A handle is an abstraction of a pointer, referring to the interface.
        try (PcapHandle handle = nif.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS , readTimeout)) {

            Thread userInputThread = new Thread(() -> {
                System.out.println("Press Enter to stop packet capture...");
                try {
                    while (System.in.available() == 0) {
                        Thread.sleep(50); // wait until input is available
                    }
                    // flush any pending input
                    while (System.in.available() > 0) {
                        System.in.read();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                if(output != null){
                    output.close();
                    System.out.println(output.count + " packets recorded in file: " + output.filename);
                }
                System.out.println(buffer.count.get() + " packets captured");
                System.exit(0);
            });

            Thread packetBuffer = new Thread(buffer::handlePacketsBuffer);

            packetBuffer.start();
            //handle.setFilter("ip6", BpfProgram.BpfCompileMode.OPTIMIZE);

            PacketListener listener = packet -> {
                byte[] data = packet.getRawData();
                L2Packet Ethernet = new L2Packet(data, nif.getName());
                buffer.addPacket(Ethernet);
            };

            userInputThread.start();
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
        String[] params = {"0.0.0.0/32", "0.0.0.0/32", "-1", "-1", "", "2"};

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

        System.out.println("Short[1] or Verbose[2]");
        input = scanner.nextLine();
        if (!input.equals("")) params[5] = input;

        return params;
    }


}