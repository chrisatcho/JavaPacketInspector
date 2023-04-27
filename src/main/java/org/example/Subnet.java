package org.example;

public class Subnet {
    private int netmask = 0;
    private int address = 0;
    private int network = 0;
    private int broadcast = 0;

    public Subnet(String cidrNotation) {
        if (cidrNotation.equals("0.0.0.0/32")) {
            return;
        }
        calculate(cidrNotation);
    }

    public boolean IPinRange(String ip) {
        int ipAddr = 0;
        String[] addressBytes = ip.split("\\.");
        for (int i = 0; i < 4; i++) {
            int b = Integer.parseInt(addressBytes[i]);
            ipAddr = ipAddr | (b << (24 - (i * 8)));
        }
        return (ipAddr & netmask) == network;
    }

    public String getNetmask() {
        return ((netmask >> 24) & 0xff) + "." + ((netmask >> 16) & 0xff) + "." + ((netmask >> 8) & 0xff) + "." + (netmask & 0xff);
    }

    private void calculate(String cidrNotation) {
        String[] addressAndMask = cidrNotation.split("/");
        int mask = Integer.parseInt(addressAndMask[1]);
        String[] addressBytes = addressAndMask[0].split("\\.");
        for (int i = 0; i < 4; i++) {
            int b = Integer.parseInt(addressBytes[i]);
            address = address | (b << (24 - (i * 8)));
        }
        netmask = 0;
        for (int i = 0; i < mask; i++) {
            netmask = netmask | (1 << (31 - i));
        }
        network = address & netmask;
        broadcast = network | ~(netmask);
    }
}
