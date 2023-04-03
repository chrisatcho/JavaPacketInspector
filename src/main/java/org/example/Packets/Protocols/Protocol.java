package org.example.Packets.Protocols;

public class Protocol {
    int sourcePort, destPort;

    Protocol(int sourcePort, int destPort){
        this.sourcePort = sourcePort;
        this.destPort = destPort;

        print();
    }

    void print(){
        System.out.println(this.sourcePort + ">>>" + this.destPort);
    }
}
