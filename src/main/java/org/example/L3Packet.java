package org.example;

// This packet handles the Network layer, but also the Data Link layer in some situations

abstract class L3Packet extends Packet {
    public abstract void parseVariables(String s);
    public abstract void printAll();
    public abstract void printPayload();
    public abstract void printSrcIP();
    public abstract void printDestIP();
    public abstract void printProtocol();
    public abstract String getProtocol();
}
