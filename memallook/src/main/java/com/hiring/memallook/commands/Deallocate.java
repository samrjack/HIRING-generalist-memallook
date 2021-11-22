package com.hiring.memallook.commands;

import com.hiring.memallook.data.Memory;
import com.hiring.memallook.io.FileHandler;
import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(name = "dealloc")
public class Deallocate implements Runnable {

    @CommandLine.Parameters(index = "0",
            description = "The tag of the memory block to be removed from " +
                    "the memory simulation.")
    String tag;

    @Override
    public void run() {
        FileHandler fileHandler = new FileHandler();
        Memory mem;
        try {
            mem = fileHandler.readMemoryFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        boolean succeeded = mem.deallocate(tag);
        if (!succeeded) {
            System.out.println("Deallocation failed, tag unknown");
            return;
        }

        try {
            new FileHandler().writeMemoryToFile(mem);
            System.out.println("Deallocation succeeded");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
