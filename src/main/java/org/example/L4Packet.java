package org.example;

public abstract class L4Packet extends Packet{
    String protocol;
    public abstract void printPayload();

    public abstract void printAll();

}
