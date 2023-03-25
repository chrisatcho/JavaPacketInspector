package org.example;

public class EthPacket {

    String srcMac,destMac, ethType, payload;

    EthPacket(byte[] bs){
        String s = "";
        int count = 0;
        for(byte b: bs){
            String hexToByte = byteToHex(b);
            s += hexToByte;
            count++;

            if(count == 5){
                this.srcMac = s;
                s = "";
            } else if(count == 11){
                this.destMac = s;
                s = "";
            } else if (count == 13){
                this.ethType = s;
                s = "";
            }
        }
        this.payload = s;

        System.out.println(this.srcMac + " " + this.destMac + " " + this.ethType + " " + this.payload.substring(2,6));

    }
    String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF); // Convert byte to hex and mask to get the last 8 bits
        if (hex.length() == 1) {
            hex = "0" + hex; // Pad with a leading 0 for single-digit hex values
        }
        return hex.toUpperCase();
    }

}
