package com.hiring.memallook.commands;

import com.hiring.memallook.data.Memory;
import com.hiring.memallook.io.FileHandler;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Optional;

@CommandLine.Command(name = "alloc",
        description = "Allocates a new block of memory in the previously " +
                "created simulation memory map.")
public class Allocate implements Runnable{

    @CommandLine.Parameters(index = "0",
            description = "The number of bytes to " +
            "allocate in the simulation memory map.")
    private int size;

    @CommandLine.Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "display help information")
    private boolean helpRequested = false;

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

        Optional<String> tag = mem.allocate(size);
        if (tag.isEmpty()) {
            System.out.println("Allocation failed.");
            return;
        }

        try {
            new FileHandler().writeMemoryToFile(mem);
            System.out.println("Ok, tag = \"" + tag.get() + "\"");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
