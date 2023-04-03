package org.example.Packets;

public class CRPackets {
    String sourceIP, destIP;
    CRPackets(String sourceIP, String destIP){
        this.sourceIP = sourceIP;
        this.destIP = destIP;

        print();
    }

    void print(){
        System.out.println(this.sourceIP + "-->" + this.destIP);
    }


}
