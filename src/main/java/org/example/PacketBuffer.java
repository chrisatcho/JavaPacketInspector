package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketBuffer {
    BlockingQueue<L2Packet> queue;
    Filter filter;

    OutputToFile output;
    AtomicInteger count;
    ARPTable arp;
    PacketBuffer(Filter filter, OutputToFile output){
        this.queue = new LinkedBlockingQueue<L2Packet>();;
        this.filter = filter;
        this.output = output;
        this.count = new AtomicInteger();
        this.arp = new ARPTable();
    }
    void addPacket(L2Packet l2){
        try{
            this.queue.put(l2);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    void handlePacketsBuffer(){

        while (true) {
            L2Packet l2;

            try {
                l2 = this.queue.take();
                count.getAndIncrement();
            }
            catch (InterruptedException e) {
                System.out.println("Interrupted");
                continue;
            }

            L3Packet l3 = PacketFactory.parseL3Packet(l2);
            L4Packet l4 = PacketFactory.parseL4Packet(l3);

            if (!filter.check(l3) || !filter.check(l4)) continue;

            if (filter.isVerbose()) {
                System.out.println("Frame " + count.get() + ": "
                        + l2.rawHex.length()/2 + " bytes captured (" + l2.rawHex.length() * 4 + " bits) on interface "
                        + l2.nif);
            }
            else {
                System.out.print("Frame " + count.get() + " ");
            }

            l2.printAll();

//            if(l3 != null) l3.printAll(this.arp.getDevID(l3.getSrcIP()), this.arp.getDevID(l3.getDestIP()), filter.isVerbose());
            if(l3 != null) l3.printAll("", "", filter.isVerbose());

            if(l4 != null) l4.printAll(filter.isVerbose());

            if(filter.isVerbose()) System.out.println("-------------------");
            else System.out.print("\n");

            if (output == null || output.closed) {
                continue;
            }

            if(output.rawHex){
                output.writeToFile(l2.getRawHex());
                continue;
            }

            StringBuilder outputLine = new StringBuilder();
            if(filter.isVerbose()){
                outputLine.append(getTime() + " " + l2.getString() + "\n");
//                if(l3 != null) outputLine.append("                " + l3.getString(this.arp.getDevID(l3.getSrcIP()), this.arp.getDevID(l3.getDestIP())) + "\n");
                if(l3 != null) outputLine.append("                " + l3.getString("", "") + "\n");
                if(l4 != null) outputLine.append("                " + l4.getString());
            }
            else{
                outputLine.append(getTime() + " " + l2.getString());
//                if(l3 != null) outputLine.append(l3.getShortString(this.arp.getDevID(l3.getSrcIP()), this.arp.getDevID(l3.getDestIP())));
                if(l3 != null) outputLine.append(l3.getShortString("", ""));
                if(l4 != null) outputLine.append(l4.getShortString());
            }
            output.writeToFile(outputLine.toString());
        }

//        while(true){
//            try {
//                if(!this.queue.isEmpty()){
//                    count.getAndIncrement();
//
//                    L2Packet Ethernet = this.queue.take();
//                    L3Packet l3 = PacketFactory.parseL3Packet(Ethernet);
//                    L4Packet l4 = PacketFactory.parseL4Packet(l3);
//
//                    if (filter.check(l3) && filter.check(l4)) {
//
//                        if(filter.isVerbose())System.out.println("Frame " + count.get() + ": "
//                                + Ethernet.rawHex.length()/2 + " bytes captured (" + Ethernet.rawHex.length() * 4 + " bits) on interface "
//                                + Ethernet.nif);
//                        else System.out.print("Frame " + count.get() + " ");
//
//                        Ethernet.printAll(filter.isVerbose());
//
//                        if(l3 != null) l3.printAll(this.arp.getDevID(l3.getSrcIP()), this.arp.getDevID(l3.getDestIP()), filter.isVerbose());
//
//                        if(l4 != null) l4.printAll(filter.isVerbose());
//
//                        if(filter.isVerbose()) System.out.println("-------------------");
//                        else System.out.print("\n");
//
//                        if(output != null && !output.closed){
//                            if(output.rawHex){
//                                output.writeToFile(Ethernet.getRawHex());
//                            }
//                            else{
//                                StringBuilder outputLine = new StringBuilder();
//
//                                if(filter.isVerbose()){
//                                   outputLine.append(getTime() + " " + Ethernet.getString() + "\n");
////                                   if(l3 != null) outputLine.append("                " + l3.getString(this.arp.getDevID(l3.getSrcIP()), this.arp.getDevID(l3.getDestIP())) + "\n");
//                                   if(l3 != null) outputLine.append("                " + l3.getString("", "") + "\n");
//                                   if(l4 != null) outputLine.append("                " + l4.getString());
//                                }
//                                else{
//                                    outputLine.append(getTime() + " " + Ethernet.getString());
////                                    if(l3 != null) outputLine.append(l3.getShortString(this.arp.getDevID(l3.getSrcIP()), this.arp.getDevID(l3.getDestIP())));
//                                    if(l3 != null) outputLine.append(l3.getShortString("", ""));
//                                    if(l4 != null) outputLine.append(l4.getShortString());
//                                }
//                                output.writeToFile(outputLine.toString());
//                            }
//                        }
//                    }
//                }
//            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//
    }
    static String getTime(){
        try{
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSSSSS");
            String formattedTime = formatter.format(now);
            return formattedTime;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
