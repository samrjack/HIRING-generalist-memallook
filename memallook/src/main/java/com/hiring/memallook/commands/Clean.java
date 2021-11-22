package com.hiring.memallook.commands;

import com.hiring.memallook.io.FileHandler;
import picocli.CommandLine;

@CommandLine.Command(name = "clean",
        description = "Completely removes the current memory simulation.")
public class Clean implements Runnable {
    @Override
    public void run() {
        new FileHandler().removeMemoryFile();
        System.out.println("Ok, memory cleared. Use 'memallook P S' again to make a new simulation.");
    }
}
