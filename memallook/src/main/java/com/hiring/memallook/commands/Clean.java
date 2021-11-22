package com.hiring.memallook.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "clean",
        description = "Completely removes the current memory simulation.")
public class Clean implements Runnable {
    @Override
    public void run() {
        System.out.println("Running clean");
    }
}
