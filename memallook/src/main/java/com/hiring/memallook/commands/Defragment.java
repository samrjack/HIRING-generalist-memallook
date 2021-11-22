package com.hiring.memallook.commands;

import com.hiring.memallook.data.Memory;
import com.hiring.memallook.io.FileHandler;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Optional;

@CommandLine.Command(name = "defrag",
        description = "Removes fragmented memory from the simulated memory.")
public class Defragment implements Runnable{

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

        mem.defragment();

        try {
            new FileHandler().writeMemoryToFile(mem);
            System.out.println("defragmentation complete.");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
