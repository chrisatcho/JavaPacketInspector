package org.example;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PcapNativeException, NotOpenException, InterruptedException {
        //String chosenInterface = getInterface();//Using user input choose an interface
        //PcapNetworkInterface networkInterface = Pcaps.getDevByName(chosenInterface);//Get the interface object

        PcapNetworkInterface networkInterface = Pcaps.getDevByName("en0");//Get the interface object

        //Params for incoming packets
        int snapshotLength = 65536;
        int readTimeout = 50;
        final PcapHandle handle = networkInterface.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);



        //Deals with the packets. More specifically, gotPacket() does
        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                EthPacket ep = new EthPacket(packet.getRawData());
                //ep.print();
            }
        };

        //Keeps the interface open and then uses the listener object to handle the packet
        handle.loop(0,listener);


    }

    /*
    This function lists the available interfaces on the device, and then you choose one
    This needs to be improved for invalid choice
     */
    static String getInterface() {
        try{
            List<PcapNetworkInterface> interfaces = Pcaps.findAllDevs();

            System.out.println("Choose one of the available interfaces: ");
            for(PcapNetworkInterface p : interfaces)System.out.println(p.getName());

            Scanner myObj = new Scanner(System.in);

            String nif = myObj.nextLine();

            while(!isValidInterface(interfaces, nif)){
                System.out.println("Invalid Interface. Please choose one of the following: ");
                for(PcapNetworkInterface p : interfaces)System.out.println(p.getName());
                nif = myObj.nextLine();
            }
            myObj.close();
            return nif;

        } catch (PcapNativeException e){
            System.out.println(e.getStackTrace());
        }
        return "Invalid Interface";
    }

    static boolean isValidInterface(List<PcapNetworkInterface> ifs, String chosenIf){
        for(PcapNetworkInterface p: ifs){
            if(p.getName().equals(chosenIf))return true;
        }
        return false;
    }



}