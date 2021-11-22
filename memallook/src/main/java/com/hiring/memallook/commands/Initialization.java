package com.hiring.memallook.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "memallook",
        mixinStandardHelpOptions = true,
        subcommands = {
                Allocate.class,
                Clean.class,
                Deallocate.class,
                Display.class
        },
        description = "Create a new memory simulation.")
public class Initialization implements Runnable{
    @CommandLine.Parameters(index = "0",
            defaultValue = "-1",
            description = "The number of pages that the simulated memory " +
                    "contains")
    private Integer numPages;

    @CommandLine.Parameters(index = "1",
            defaultValue = "-1",
            description = "The maximum number of bytes that fit in each page.")
    private Integer numBytesPerPage;

    @Override
    public void run() {
        if (numPages < 0 || numBytesPerPage < 0)
            throw new IllegalArgumentException("Didn't pass in correct parameters. See the help for usage details.");
        System.out.println("Running initilization");
    }
}
