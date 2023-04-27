package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;

public class OutputToFile {
    BufferedWriter writer;
    public boolean rawHex;
    int amountToRecord;
    public boolean sizeBuffer;
    int count;
    public boolean closed;
    public String filename;
    OutputToFile(String fileName, boolean rawHex, int amountToRecord){
        try {
            this.filename = fileName;
            this.writer = new BufferedWriter(new FileWriter(fileName));
            this.rawHex = rawHex;
            this.sizeBuffer = amountToRecord != -1;
            this.amountToRecord = amountToRecord;
            this.count = 0;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String packetToWrite){
        try{
            if(!this.closed && this.count >= this.amountToRecord && this.sizeBuffer){
                writer.close();
                this.closed = true;
            }
            else{
                this.count++;
                writer.write(packetToWrite +"\n");
                writer.flush();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    static OutputToFile getOutputToFile(){
        Scanner inputScanner = new Scanner(System.in);
        boolean rawHex;
        int amountToRecord;
        try{
            System.out.println("Would you like to output the packets to a file? y/n: ");
            String ans = inputScanner.nextLine();

            if(!ans.equalsIgnoreCase("y"))return null;

            System.out.println("Would you like the packets in Raw Hex [1], or already decoded [2]? ");
            ans = inputScanner.nextLine();

            rawHex = ans.equals("1");

            System.out.println("Would you like to record a certain amount of packets? Either input the amount to record, or press Enter for no.");
            ans = inputScanner.nextLine();

            if(ans.equals(""))amountToRecord = -1;
            else amountToRecord = Integer.parseInt(ans);

            System.out.println("What would you like to call the file? ");
            ans = inputScanner.nextLine();

            return new OutputToFile(ans, rawHex, amountToRecord);

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
