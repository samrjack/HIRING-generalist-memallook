package com.hiring.memallook.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "show",
        description = "Prints the simulated memory to the screen in a human " +
                "readable fashion.")
public class Display implements Runnable {

    @Override
    public void run() {
        System.out.println("Running Display");
    }
}
