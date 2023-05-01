package org.example;

public abstract class L4Packet extends Packet{
    String protocol;
    public abstract void printPayload();

    public abstract void printAll(boolean verbose);

    public abstract int getSourcePort();

    public abstract  int getDestPort();

    public abstract String getString();

    public abstract String getShortString();

}
