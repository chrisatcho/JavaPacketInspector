package org.example;

import java.io.FileWriter;
import java.io.IOException;
public class OutputToFile {
    FileWriter writer;
    OutputToFile(String fileName){
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void writeToFile(String packetToWrite){
        try{
            writer.write(packetToWrite);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    void close(){
        try{
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
