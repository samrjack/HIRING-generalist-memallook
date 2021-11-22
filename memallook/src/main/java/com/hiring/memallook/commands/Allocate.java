package com.hiring.memallook.commands;

import picocli.CommandLine;

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
        System.out.println("Running alloc");
    }
}
