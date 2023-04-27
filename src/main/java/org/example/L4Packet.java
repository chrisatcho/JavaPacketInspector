package org.example;

public abstract class L4Packet extends Packet{
    String protocol;
    public abstract void printPayload();

    public abstract void printAll();

    public abstract int getSourcePort();

    public abstract  int getDestPort();

    public abstract String getString();

}
