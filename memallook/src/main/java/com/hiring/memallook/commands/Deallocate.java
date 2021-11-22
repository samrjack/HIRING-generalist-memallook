package com.hiring.memallook.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "dealloc")
public class Deallocate implements Runnable {

    @CommandLine.Parameters(index = "0",
            description = "The tag of the memory block to be removed from " +
                    "the memory simulation.")
    String tag;

    @Override
    public void run() {
        System.out.println("Running Dealloc");
    }
}
